package com.example.razorsyncdemo

import androidx.lifecycle.ViewModel
import com.example.razorsyncdemo.database.PokemonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.razorsyncdemo.repository.Repository
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _viewStateFlow = MutableStateFlow(ListViewViewState())
    val viewState: StateFlow<ListViewViewState> = _viewStateFlow

    suspend fun getPokemon() : List<PokemonEntity>{
        _viewStateFlow.value = ListViewViewState(isLoading = false)
        return repository.getPokemon()
    }
}

data class ListViewViewState(
    val isLoading: Boolean = true,
)