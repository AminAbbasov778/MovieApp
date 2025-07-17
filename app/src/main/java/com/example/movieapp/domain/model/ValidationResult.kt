package com.example.movieapp.domain.model

data class ValidationResult(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isValid: Boolean = false
)