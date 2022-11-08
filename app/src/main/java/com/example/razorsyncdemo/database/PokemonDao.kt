package com.example.razorsyncdemo.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PokemonDao {

    @Query("SELECT * FROM POKEMON_TABLE")
    fun getAll(): PagingSource<Int, PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<PokemonEntity>)

    @Query("DELETE FROM POKEMON_TABLE")
    suspend fun deleteList()
}
