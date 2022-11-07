package com.example.razorsyncdemo.network

import com.example.razorsyncdemo.model.PokemonDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface PokemonApiService {
    @GET("pokemon")
    suspend fun getListOfPokemon(@Query("offset") offset :Int, @Query("limit") pageSize:Int) : Response<PokemonListPageResponse>

    @GET
    suspend fun getPokemon(@Url url: String): PokemonDetailResponse
}
