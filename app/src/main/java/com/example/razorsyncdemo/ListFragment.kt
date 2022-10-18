package com.example.razorsyncdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.razorsyncdemo.database.PokemonEntity
import com.example.razorsyncdemo.databinding.FragmentFirstBinding
import com.example.razorsyncdemo.databinding.ListItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ListFragment : Fragment() {
    private val viewModel by viewModels<ListViewModel>()
    private val adapter = CustomAdapter()

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.submitList(viewModel.getPokemon())
                viewModel.viewState.collect() {
                    binding.recyclerView.visibility = if(it.isLoading) View.GONE else View.VISIBLE
                    binding.progressBar.visibility = if(it.isLoading) View.VISIBLE else View.GONE
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

class CustomAdapter() : ListAdapter<PokemonEntity,CustomAdapter.ViewHolder>(PokemonEntityDiffUtil()) {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    // Holds the views for adding it to image and text
    class ViewHolder(private val itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(pokemonEntity: PokemonEntity) {
            itemBinding.tvName.text = pokemonEntity.name
        }
    }

    class PokemonEntityDiffUtil : DiffUtil.ItemCallback<PokemonEntity>() {
        override fun areItemsTheSame(
            oldItem: PokemonEntity,
            newItem: PokemonEntity
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: PokemonEntity,
            newItem: PokemonEntity
        ): Boolean = newItem.name == oldItem.name
    }
}
