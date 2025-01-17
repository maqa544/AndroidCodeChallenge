package com.example.razorsyncdemo.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [
        PokemonEntity::class, PokemonRemoteKeys::class, FavoriteEntity::class], version = 1
)

abstract class AppDatabase : RoomDatabase() {
    internal abstract fun pokemonDao(): PokemonDao
    internal abstract fun pokemonRemoteKeysDao(): PokemonRemoteKeysDao
    internal abstract fun favoriteDao(): FavoriteDao
}