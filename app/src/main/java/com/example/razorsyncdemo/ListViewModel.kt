package com.example.razorsyncdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.razorsyncdemo.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _viewStateFlow = MutableStateFlow(ListViewViewState())
    val viewState: StateFlow<ListViewViewState> = _viewStateFlow

    val getAllPokemons = repository.getAllPokemons()
//    suspend fun getPokemon() : List<PokemonEntity>{
//        _viewStateFlow.value = ListViewViewState(isLoading = false)
//        return repository.getPokemon()
//    }

    val allFavorites = repository.readAllFavorite

    fun addFavorite(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(name)
        }
    }

    fun deleteFavorite(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(name)
        }
    }
}

data class ListViewViewState(
    val isLoading: Boolean = true,
)