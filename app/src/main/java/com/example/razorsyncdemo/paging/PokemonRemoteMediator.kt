package com.example.razorsyncdemo.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.razorsyncdemo.database.AppDatabase
import com.example.razorsyncdemo.database.PokemonEntity
import com.example.razorsyncdemo.model.PokemonRemoteKeys
import com.example.razorsyncdemo.network.PokemonApiService
import com.example.razorsyncdemo.util.Constants.PER_PAGE_SIZE
import com.example.razorsyncdemo.util.Constants.STARTING_OFFSET
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator @Inject constructor(
    private val appDatabase: AppDatabase,
    private val pokemonApiService: PokemonApiService
): RemoteMediator<Int, PokemonEntity>() {
    private val pokemonDao = appDatabase.pokemonDao()
    private val pokemonRemoteKeysDao = appDatabase.pokemonRemoteKeysDao()


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {

        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
//                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                    remoteKeys?.nextPage?.minus(1) ?: 1
                    0
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = pokemonApiService.getListOfPokemon(offset = currentPage, currentPage*state.config.pageSize)
            val endOfPaginationReached = response.body()?.pokemon!!.isEmpty()
            val pokemonList = response.body()?.pokemon

            val prevPage = if (currentPage == STARTING_OFFSET) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDao.deleteAll()
                    pokemonRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = pokemonList?.map { pokemon ->
                    PokemonRemoteKeys(
                        id = pokemon.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                pokemonList?.let {
                    pokemonDao.insertList(it)
                }
                keys?.let {
                    pokemonRemoteKeysDao.insertAllRemoteKeys(remoteKeys = it)
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PokemonEntity>
    ): PokemonRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { key ->
                pokemonRemoteKeysDao.getRemoteKeys(id = key.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PokemonEntity>
    ): PokemonRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { key ->
                pokemonRemoteKeysDao.getRemoteKeys(id = key.id)
            }
    }
}