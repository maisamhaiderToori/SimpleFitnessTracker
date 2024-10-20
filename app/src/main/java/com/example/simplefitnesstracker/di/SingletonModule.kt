package com.example.simplefitnesstracker.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class SingletonModule {

    @Singleton
    @Provides
    fun getFirebaseInstance(): FirebaseAuth  = FirebaseAuth.getInstance()
}