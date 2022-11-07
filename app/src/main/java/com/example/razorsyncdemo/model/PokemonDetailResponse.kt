package com.example.razorsyncdemo.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("id") val pokemonId: Int,
    @SerializedName("name") val pokemonName: String,
    @SerializedName("weight") val pokemonWeight: Int,
    @SerializedName("height") val pokemonHeight: Int,
)
