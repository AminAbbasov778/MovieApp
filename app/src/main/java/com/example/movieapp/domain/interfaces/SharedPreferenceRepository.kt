package com.example.movieapp.domain.interfaces

interface SharedPreferenceRepository {
    fun saveRegister(isRegistered: Boolean)
}