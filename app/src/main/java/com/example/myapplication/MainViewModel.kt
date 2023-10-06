package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import perfetto.protos.UiState
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel :ViewModel() {

    // Estado observable por Compose
    private val _state = MutableStateFlow (UiState())
        val state:StateFlow<UiState> = _state
    init {
        viewModelScope.launch { //genera el contexto para corutina
            //peticion al servidor
            _state.value = UiState (loading = true)
            delay(1000)
            _state.value = UiState (
                loading = false,
                movies = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService::class.java)
                .getMovies()
                .results)
        }
    }
    fun onMovieClick(movie: ServerMovie) {
        val movies = _state.value.movies.toMutableList()
        movies.replaceAll { if (it.id == movie.id) movie.copy(favorite = !movie.favorite) else it}
        _state.value = _state.value.copy(movies = movies)

    }
    data class UiState(
        val loading: Boolean = false,
        val movies: List<ServerMovie> = emptyList()
    )
    }
