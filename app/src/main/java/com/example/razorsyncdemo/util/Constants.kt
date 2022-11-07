package com.example.razorsyncdemo.util

import androidx.recyclerview.widget.DiffUtil
import com.example.razorsyncdemo.database.PokemonEntity

object Constants {

    const val APP_DATABASE_NAME = "POKEMON_APP_DATABASE"
    const val POKEMON_REMOTE_KEYS_TABLE = "pokemon_remote_keys_table"
    const val POKEMON_TABLE = "pokemon_table"
    const val PER_PAGE_SIZE = 20
    const val STARTING_OFFSET = 0
    const val EMPTY_DATA = "EMPTY"

    val listPokemonAdapterCallback = object : DiffUtil.ItemCallback<PokemonEntity>() {
        override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity): Boolean {
            return oldItem == newItem
        }
    }

}