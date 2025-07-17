package com.example.movieapp.domain.usecases

import com.example.movieapp.domain.interfaces.SharedPreferenceRepository
import javax.inject.Inject

class SaveRegisterUseCase @Inject constructor(private val sharedPreferenceRepository: SharedPreferenceRepository) {
    operator fun invoke(isRegistered: Boolean) {
        sharedPreferenceRepository.saveRegister(isRegistered)
    }

}