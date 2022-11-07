package com.example.razorsyncdemo.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_table")
    fun getAll(): PagingSource<Int,PokemonEntity>

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<PokemonEntity>
}