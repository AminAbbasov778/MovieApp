package com.example.movieapp.di

import android.content.SharedPreferences
import com.example.movieapp.data.AuthRepositoryImpl
import com.example.movieapp.data.SharedPreferenceRepositoryImpl
import com.example.movieapp.domain.interfaces.AuthRepository
import com.example.movieapp.domain.interfaces.SharedPreferenceRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideSharedPreferenceRepository(sharedPreferences: SharedPreferences): SharedPreferenceRepository = SharedPreferenceRepositoryImpl(sharedPreferences)
}