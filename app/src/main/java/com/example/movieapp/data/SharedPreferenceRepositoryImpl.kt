package com.example.movieapp.data

import android.content.SharedPreferences
import com.example.movieapp.domain.interfaces.SharedPreferenceRepository
import javax.inject.Inject

class SharedPreferenceRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences):
    SharedPreferenceRepository {
    override fun saveRegister(isRegistered: Boolean) {
        sharedPreferences.edit().putBoolean("isRegistered", isRegistered).apply()
    }
}