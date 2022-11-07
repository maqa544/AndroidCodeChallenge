package com.example.razorsyncdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.razorsyncdemo.util.Constants.POKEMON_REMOTE_KEYS_TABLE

@Entity (tableName = POKEMON_REMOTE_KEYS_TABLE)
data class PokemonRemoteKeys(
    @PrimaryKey val id : Int,
    val prevPage: Int?,
    val nextPage: Int?
)
