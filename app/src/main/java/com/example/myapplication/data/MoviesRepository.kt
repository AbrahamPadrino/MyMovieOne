package com.example.myapplication.data

import com.example.myapplication.data.local.LocalDataSouce
import com.example.myapplication.data.remote.RemoteDataSouce
import kotlinx.coroutines.flow.Flow

class MoviesRepository(
    private val remoteDataSource: RemoteDataSouce,
    private val localDataSource: LocalDataSouce
) {
    val movies: Flow<List<Movie>> = localDataSource.movies
    suspend fun updateMovie(movie: Movie) {
        localDataSource.updateMovie(movie)
    }
    suspend fun requestMovies() {
        val isDbEmpty = localDataSource.count() == 0
        if (isDbEmpty) {
            localDataSource.insertAll(remoteDataSource.getMovies())
        }
    }
}
