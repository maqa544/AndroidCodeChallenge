package com.example.razorsyncdemo.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.razorsyncdemo.model.PokemonRemoteKeys

@Dao
interface PokemonRemoteKeysDao {
    @Query("SELECT * FROM pokemon_remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): PokemonRemoteKeys

    @Query("DELETE FROM pokemon_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKeys: List<PokemonRemoteKeys>)
}