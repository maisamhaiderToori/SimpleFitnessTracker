package com.example.simplefitnesstracker.di

import com.example.simplefitnesstracker.data.repositories.FirebaseRepository
import com.example.simplefitnesstracker.data.repositories.FirebaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoriesModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: FirebaseRepositoryImpl): FirebaseRepository
}