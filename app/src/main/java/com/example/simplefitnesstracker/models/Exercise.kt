package com.example.simplefitnesstracker.models

data class Exercise(
    val id: String = "",
    val name: String,
    val description: String,
    val duration: String,
    val status: String,
    val date: String
)
