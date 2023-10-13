package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.room.Room
import com.example.myapplication.data.MoviesRepository
import com.example.myapplication.data.local.LocalDataSouce
import com.example.myapplication.data.local.MoviesDatabase
import com.example.myapplication.data.remote.RemoteDataSouce
import com.example.myapplication.ui.screens.home.Home


// MVVM - Model View ViewModel
class MainActivity : ComponentActivity() {

    //instancia a la Base de Datos
    private lateinit var db : MoviesDatabase
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(
            applicationContext,
            MoviesDatabase::class.java, "movies-db"
        ).build()

        val repository = MoviesRepository(
            remoteDataSource = RemoteDataSouce(),
            localDataSource = LocalDataSouce(db.moviesDao())
        )

        setContent {
            Home(repository)

    }

}

}