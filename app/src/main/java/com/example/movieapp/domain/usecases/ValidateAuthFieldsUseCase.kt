package com.example.movieapp.domain.usecases

import com.example.movieapp.R
import com.example.movieapp.domain.model.ValidationResult
import javax.inject.Inject

class ValidateAuthFieldsUseCase @Inject constructor() {

    operator fun invoke(email: String, password: String): ValidationResult {
        val emailError = when {
            email.isBlank() -> R.string.email_boş_ola_bilməz
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.email_formatı_səhvdir
            else -> null
        }

        val passwordError = when {
            password.isBlank() -> R.string.sifrə_boş_ola_bilməz
            password.length < 6 -> R.string.sifrə_ən_azı_6_simvol_olmalıdır
            else -> null
        }

        val isValid = emailError == null && passwordError == null
        return ValidationResult(emailError, passwordError, isValid)
    }
}