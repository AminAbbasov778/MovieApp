package com.example.movieapp.domain.usecases

import com.example.movieapp.domain.interfaces.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor (private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.signIn(email, password)
    }
}