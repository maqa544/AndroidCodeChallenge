package com.example.razorsyncdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.razorsyncdemo.database.PokemonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.razorsyncdemo.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _viewStateFlow = MutableStateFlow(ListViewViewState())
    val viewState: StateFlow<ListViewViewState> = _viewStateFlow

    val pokemonListPaging =
        repository.getPokemon().cachedIn(viewModelScope)

}

data class ListViewViewState(
    val isLoading: Boolean = true,
)