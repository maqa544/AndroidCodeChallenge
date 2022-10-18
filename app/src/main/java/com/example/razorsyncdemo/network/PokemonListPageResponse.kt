package com.example.razorsyncdemo.network

import com.example.razorsyncdemo.database.PokemonEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonListPageResponse (
    @Json(name = "count")
    val count : Int?,
    @Json(name = "next")
    val nextUrl: String?,
    @Json(name = "previous")
    val previousUrl: String?,
    @Json(name = "results")
    val pokemon :List<PokemonEntity>?
    )
