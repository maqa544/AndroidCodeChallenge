package com.example.razorsyncdemo.database
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [
     PokemonEntity::class
    ], version = 1
)

abstract class AppDatabase : RoomDatabase() {
    internal abstract fun pokemonDao(): PokemonDao
}