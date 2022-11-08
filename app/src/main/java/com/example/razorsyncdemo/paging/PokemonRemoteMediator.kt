package com.example.razorsyncdemo.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.razorsyncdemo.database.AppDatabase
import com.example.razorsyncdemo.database.PokemonEntity
import com.example.razorsyncdemo.database.PokemonRemoteKeys
import com.example.razorsyncdemo.network.PokemonApiService
import com.example.razorsyncdemo.util.Constants.PER_PAGE_SIZE
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator @Inject constructor(
    private val appDatabase: AppDatabase,
    private val pokemonApiService: PokemonApiService
) : RemoteMediator<Int, PokemonEntity>() {
    private val pokemonDao = appDatabase.pokemonDao()
    private val pokemonRemoteKeysDao = appDatabase.pokemonRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPageOffset?.minus(PER_PAGE_SIZE) ?: 0
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPageOffset
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPageOffset
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

//            return pokemonApiService.getListOfPokemon(page * PER_PAGE_SIZE, PER_PAGE_SIZE)
//                .body()?.pokemon ?: listOf()

            val response =
                pokemonApiService.getListOfPokemon(offset = currentPage, pageSize = PER_PAGE_SIZE)
                    .body()?.pokemon ?: listOf()
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 0) null else currentPage - PER_PAGE_SIZE
            val nextPage = if (endOfPaginationReached) null else currentPage + PER_PAGE_SIZE

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDao.deleteList()
                    pokemonRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { pokemon ->
                    PokemonRemoteKeys(
                        id = pokemon.name ?: "",
                        prevPageOffset = prevPage,
                        nextPageOffset = nextPage
                    )
                }
                pokemonRemoteKeysDao.insertAllRemoteKeys(remoteKeys = keys)
                pokemonDao.insertList(list = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokemonEntity>
    ): PokemonRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { name ->
                pokemonRemoteKeysDao.getRemoteKeys(id = name)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PokemonEntity>
    ): PokemonRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemonEntity ->
                pokemonRemoteKeysDao.getRemoteKeys(id = pokemonEntity.name ?: "")
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PokemonEntity>
    ): PokemonRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemonEntity ->
                pokemonRemoteKeysDao.getRemoteKeys(id = pokemonEntity.name ?: "")
            }
    }
}