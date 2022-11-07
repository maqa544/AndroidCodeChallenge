package com.example.razorsyncdemo.database

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.razorsyncdemo.base.BaseSource
import com.example.razorsyncdemo.model.Results
import com.example.razorsyncdemo.network.PokemonApiService
import com.example.razorsyncdemo.util.Constants.EMPTY_DATA
import com.example.razorsyncdemo.util.Constants.PER_PAGE_SIZE
import com.example.razorsyncdemo.util.Constants.STARTING_OFFSET
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonRemoteDataSourceImpl @Inject constructor(
    baseSource: BaseSource,
    private val api: PokemonApiService
) : PokemonRemoteDataSource, BaseSource by baseSource {

    override fun getPagingPokemon(): PagingSource<Int, PokemonEntity> {
        return object : PagingSource<Int, PokemonEntity>(){
            override fun getRefreshKey(state: PagingState<Int, PokemonEntity>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

                }
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonEntity> {
                val position = params.key ?: STARTING_OFFSET
                return try {
                    when (val response =
                        oneShotCalls { api.getListOfPokemon(PER_PAGE_SIZE, PER_PAGE_SIZE) }) {
                        is Results.Success -> {

                            if (response.data.pokemon.isNullOrEmpty()){
                                LoadResult.Error(Throwable(EMPTY_DATA))
                            }else {
                                LoadResult.Page(
                                    data = response.data.pokemon,
                                    prevKey = if (position == STARTING_OFFSET) null else position - 1,
                                    nextKey = if (response.data.pokemon.isEmpty()) null else position + 1
                                )
                            }

                        }
                        is Results.Error -> {
                            LoadResult.Error(response.exception)
                        }
                    }
                } catch (exception: IOException) {
                    LoadResult.Error(exception)
                } catch (exception: HttpException) {
                    LoadResult.Error(exception)
                }
            }
        }
    }
}