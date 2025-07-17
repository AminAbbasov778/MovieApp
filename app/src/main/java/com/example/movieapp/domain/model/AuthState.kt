package com.example.movieapp.domain.model

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: Int): AuthState()
    data class Error(val message: Int) : AuthState()
}