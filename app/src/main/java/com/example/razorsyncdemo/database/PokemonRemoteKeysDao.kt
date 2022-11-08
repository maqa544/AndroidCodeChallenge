package com.example.razorsyncdemo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonRemoteKeysDao {
    @Query("SELECT * FROM pokemon_remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: String): PokemonRemoteKeys

    @Query("DELETE FROM pokemon_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKeys: List<PokemonRemoteKeys>)
}