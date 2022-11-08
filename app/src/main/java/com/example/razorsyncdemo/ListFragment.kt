package com.example.razorsyncdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.razorsyncdemo.base.BaseFragment
import com.example.razorsyncdemo.database.PokemonEntity
import com.example.razorsyncdemo.databinding.FragmentFirstBinding
import com.example.razorsyncdemo.databinding.ListItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@OptIn(ExperimentalPagingApi::class)
@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentFirstBinding>() {
    @OptIn(ExperimentalPagingApi::class)
    private val viewModel by viewModels<ListViewModel>()
    private val adapter = CustomAdapter()
    var favoriteListPublic = listOf<String>()

    override fun getLayoutID(): Int = R.layout.fragment_first

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            viewModel.getAllPokemons.distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun observeData() {
        viewModel.allFavorites.observe(viewLifecycleOwner) {
            favoriteListPublic = it.map {
                it.favoriteName
            }
        }
        super.observeData()
    }

    inner class CustomAdapter() :
        PagingDataAdapter<PokemonEntity, CustomAdapter.ViewHolder>(PokemonEntityDiffUtil()) {

        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemBinding =
                ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(itemBinding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            getItem(position)?.let {
                holder.bind(it)
            }
        }

        // Holds the views for adding it to image and text
        inner class ViewHolder(private val itemBinding: ListItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(pokemonEntity: PokemonEntity) {
                itemBinding.tvName.text = pokemonEntity.name
                var isFavorite =
                    favoriteListPublic.contains(pokemonEntity.name).also { setFavoriteView(it) }
                itemBinding.clRoot.setOnClickListener {
                    isFavorite = if (isFavorite) {
                        pokemonEntity.name?.let {
                            deleteFavorite(pokemonEntity.name)
                        }
                        false
                    } else {
                        pokemonEntity.name?.let {
                            addFavorite(pokemonEntity.name)
                        }
                        true
                    }
                }
            }

            private fun addFavorite(name: String) {
                viewModel.addFavorite(name)
                setFavoriteView(true)
            }

            private fun deleteFavorite(name: String) {
                viewModel.deleteFavorite(name)
                setFavoriteView(false)
            }

            private fun setFavoriteView(isFavorite: Boolean) {
                itemBinding.imFavorite.isVisible = isFavorite
            }
        }
    }

    inner class PokemonEntityDiffUtil : DiffUtil.ItemCallback<PokemonEntity>() {
        override fun areItemsTheSame(
            oldItem: PokemonEntity,
            newItem: PokemonEntity
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PokemonEntity,
            newItem: PokemonEntity
        ): Boolean = newItem.id == oldItem.id
    }
}
