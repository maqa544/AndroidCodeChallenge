package com.example.razorsyncdemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.razorsyncdemo.util.Constants.POKEMON_TABLE
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(
    tableName = POKEMON_TABLE
)
data class PokemonEntity (
    @Json(name = "name")
    val name : String?,
    @Json(name = "url")
    val Url : String?
    ) {
    @Json(ignore = true)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}