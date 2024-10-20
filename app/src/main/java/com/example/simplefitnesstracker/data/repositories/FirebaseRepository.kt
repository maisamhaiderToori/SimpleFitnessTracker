package com.example.simplefitnesstracker.data.repositories

interface FirebaseRepository {
    fun signUp(email: String, password: String)
}

class FirebaseRepositoryImpl : FirebaseRepository {
    override fun signUp(email: String, password: String) {
        TODO("Not yet implemented")
    }
}