package com.example.razorsyncdemo.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.razorsyncdemo.database.PokemonEntity
import com.example.razorsyncdemo.databinding.ListItemBinding
import com.example.razorsyncdemo.util.Constants

class PokemonPagingAdapter() :
PagingDataAdapter<PokemonEntity, PokemonPagingAdapter.ViewHolder>(Constants.listPokemonAdapterCallback) {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    // Holds the views for adding it to image and text
    class ViewHolder(private val itemBinding: ListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(pokemonEntity: PokemonEntity) {
            itemBinding.tvName.text = pokemonEntity.name
        }
    }
}