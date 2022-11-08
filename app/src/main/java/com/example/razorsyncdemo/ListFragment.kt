package com.example.razorsyncdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentFirstBinding>() {
    @OptIn(ExperimentalPagingApi::class)
    private val viewModel by viewModels<ListViewModel>()
    private val adapter = CustomAdapter()

    override fun getLayoutID(): Int = R.layout.fragment_first

    @OptIn(ExperimentalPagingApi::class)
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
}

class CustomAdapter() :
    PagingDataAdapter<PokemonEntity, CustomAdapter.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<PokemonEntity>() {
            override fun areItemsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PokemonEntity, newItem: PokemonEntity) =
                oldItem.id == newItem.id
        }
    }

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
    class ViewHolder(private val itemBinding: ListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(pokemonEntity: PokemonEntity) {
            itemBinding.tvName.text = pokemonEntity.name
        }
    }
}
