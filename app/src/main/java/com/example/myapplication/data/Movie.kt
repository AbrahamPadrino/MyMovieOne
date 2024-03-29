package com.example.myapplication.data

import androidx.room.Entity

@Entity
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val favorite: Boolean
)
