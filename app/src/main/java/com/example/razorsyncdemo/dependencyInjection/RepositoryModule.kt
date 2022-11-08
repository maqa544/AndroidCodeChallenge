package com.example.razorsyncdemo.dependencyInjection

import com.example.razorsyncdemo.database.AppDatabase
import com.example.razorsyncdemo.network.PokemonApiService
import com.example.razorsyncdemo.repository.Repository
import com.example.razorsyncdemo.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun bindsRepository(
        pokemonApiService: PokemonApiService,
        appDatabase: AppDatabase
    ): Repository {
        return RepositoryImpl(appDatabase, pokemonApiService)
    }
}