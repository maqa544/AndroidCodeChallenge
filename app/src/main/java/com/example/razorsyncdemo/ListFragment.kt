package com.example.razorsyncdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.razorsyncdemo.adpater.PokemonPagingAdapter
import com.example.razorsyncdemo.base.BaseFragment
import com.example.razorsyncdemo.database.PokemonEntity
import com.example.razorsyncdemo.databinding.FragmentFirstBinding
import com.example.razorsyncdemo.databinding.ListItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentFirstBinding>() {
    private val viewModel by viewModels<ListViewModel>()
    private lateinit var pokemonAdapter: PokemonPagingAdapter

    override fun getLayoutID(): Int = R.layout.fragment_first

    override fun setUpViews() {

        super.setUpViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        pokemonAdapter = PokemonPagingAdapter()
//        binding.recyclerView.adapter = pokemonAdapter
        with(binding) {
            initView()
        }
        getData()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun FragmentFirstBinding.initView() {
        recyclerView.apply {
            adapter = pokemonAdapter
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            pokemonAdapter.loadStateFlow.onEach { loadState ->
            }.launchIn(this)
        }
    }
    private fun getData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pokemonListPaging.collectLatest {
                pokemonAdapter.submitData(it)
            }
        }
    }
}
