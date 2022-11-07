package com.example.razorsyncdemo.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PokemonApiService {
    @GET("pokemon")
    suspend fun getListOfPokemon(@Query("offset") offset :Int, @Query("limit") pageSize:Int) : Response<PokemonListPageResponse>
}
