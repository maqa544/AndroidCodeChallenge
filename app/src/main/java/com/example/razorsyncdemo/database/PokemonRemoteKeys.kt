package com.example.razorsyncdemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.razorsyncdemo.util.Constants.POKEMON_REMOTE_KEYS_TABLE

@Entity(tableName = POKEMON_REMOTE_KEYS_TABLE)
data class PokemonRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPageOffset: Int?,
    val nextPageOffset: Int?
)
