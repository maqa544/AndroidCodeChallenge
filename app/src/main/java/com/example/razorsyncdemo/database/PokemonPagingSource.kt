package com.example.razorsyncdemo.database

import androidx.paging.PagingSource

interface PokemonRemoteDataSource {

    fun getPagingPokemon(): PagingSource<Int, PokemonEntity>
}