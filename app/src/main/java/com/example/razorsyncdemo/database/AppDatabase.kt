package com.example.razorsyncdemo.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.razorsyncdemo.model.PokemonRemoteKeys


@Database(
    entities = [
     PokemonEntity::class, PokemonRemoteKeys::class
    ], version = 1
)

abstract class AppDatabase : RoomDatabase() {
    internal abstract fun pokemonDao(): PokemonDao
    internal abstract fun pokemonRemoteKeysDao(): PokemonRemoteKeysDao
}