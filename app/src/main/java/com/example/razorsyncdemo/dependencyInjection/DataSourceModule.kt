package com.example.razorsyncdemo.dependencyInjection

import com.example.razorsyncdemo.base.BaseSource
import com.example.razorsyncdemo.base.BaseSourceImpl
import com.example.razorsyncdemo.database.PokemonRemoteDataSource
import com.example.razorsyncdemo.database.PokemonRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ian Damping on 07,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsBaseSource(source: BaseSourceImpl): BaseSource

    @Binds
    fun bindsRemoteDataSource(source: PokemonRemoteDataSourceImpl): PokemonRemoteDataSource
}