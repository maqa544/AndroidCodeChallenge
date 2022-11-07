package com.example.razorsyncdemo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertList(list: List<PokemonEntity>)

    @Query("SELECT * FROM Pokemon")
    suspend fun getAll(): List<PokemonEntity>?
}