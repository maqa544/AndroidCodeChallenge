package com.example.razorsyncdemo.dependencyInjection

import android.content.Context
import androidx.room.Room
import androidx.viewbinding.BuildConfig
import com.example.razorsyncdemo.database.AppDatabase
import com.example.razorsyncdemo.database.PokemonDao
import com.example.razorsyncdemo.database.PokemonRemoteKeysDao
import com.example.razorsyncdemo.util.Constants.APP_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase {
        var database = Room.databaseBuilder(appContext, AppDatabase::class.java, APP_DATABASE_NAME)
            .enableMultiInstanceInvalidation()
        if (BuildConfig.DEBUG) {
            database = database.fallbackToDestructiveMigration()
        }
        return database.build()
    }

    @Provides
    fun providesPokemonDao(appDatabase: AppDatabase) : PokemonDao =
        appDatabase.pokemonDao()

//    @Provides
//    fun providesPokemonRemoteKeysDao(appDatabase: AppDatabase) : PokemonRemoteKeysDao =
//        appDatabase.pokemonRemoteKeysDao()

}