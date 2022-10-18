package com.example.razorsyncdemo.dependencyInjection

import com.example.razorsyncdemo.network.PokemonApiService
import com.example.razorsyncdemo.database.PokemonDao
import com.example.razorsyncdemo.repository.Repository
import com.example.razorsyncdemo.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun bindsRepository (pokemonApiService: PokemonApiService, pokemonDao: PokemonDao) : Repository {
        return RepositoryImpl(pokemonDao, pokemonApiService)
    }
}