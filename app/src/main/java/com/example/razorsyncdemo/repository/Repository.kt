package com.example.razorsyncdemo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.razorsyncdemo.database.AppDatabase
import com.example.razorsyncdemo.database.PokemonEntity
import com.example.razorsyncdemo.network.PokemonApiService
import com.example.razorsyncdemo.paging.PokemonRemoteMediator
import com.example.razorsyncdemo.util.Constants.PER_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*

Your first task is to modify this app to allow the app to show the list of Pokemon without having to
reload the data from the API everytime the application is started, ie. Cache the results of the
API. Feel free to do this any way you see fit, however a Room Database instance has been provided to
you for convenience.

Follow the Single source of truth principle and Repository Architecture documented here
https://developer.android.com/codelabs/basic-android-kotlin-training-repository-pattern#0

Your Second task is to show all of the pokemon the API has instead of the first 20. Feel free to do
this however you see fit. The performance of your implementation will be scrutinized.

Your Optional bonus task is to add the ability to favorite a pokemon by tapping them on the list,
and persisting the favorite status. This task will require more application wide changes then just
this file.

Feel free to make changes to any file in the application you need to complete the tasks.
You may include and use any additional first or third party libraries to help you.

This File is where the majority of your work should take place.
Additional resources:
https://developer.android.com/topic/libraries/architecture/paging/v3-overview


 */

interface Repository {
    fun getAllPokemons(): Flow<PagingData<PokemonEntity>>
}

class RepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val pokemonApiService: PokemonApiService
) :
    Repository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllPokemons(): Flow<PagingData<PokemonEntity>> {
        val pagingSourceFactory = { appDatabase.pokemonDao().getAll() }

        return Pager(
            config = PagingConfig(pageSize = PER_PAGE_SIZE),
            remoteMediator = PokemonRemoteMediator(
                appDatabase = appDatabase,
                pokemonApiService = pokemonApiService
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}