package com.example.movieapp.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.R
import com.example.movieapp.domain.model.AuthState
import com.example.movieapp.domain.model.ValidationResult
import com.example.movieapp.domain.usecases.SaveRegisterUseCase
import com.example.movieapp.domain.usecases.SignInUseCase
import com.example.movieapp.domain.usecases.SignUpUseCase
import com.example.movieapp.domain.usecases.ValidateAuthFieldsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor (private val saveRegisterUseCase: SaveRegisterUseCase,
                      private val signUpUseCase: SignUpUseCase,
                      private val validateAuthFieldsUseCase: ValidateAuthFieldsUseCase
): ViewModel() {

    var passwordVisible = mutableStateOf(false)

    var email = mutableStateOf("")
        private set
    var password = mutableStateOf("")
        private set

    var validationState = mutableStateOf(ValidationResult())
        private set

    var authState = mutableStateOf<AuthState>(AuthState.Idle)
        private set

    var isChecked = mutableStateOf(false)
        private set

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
        validate()
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
        validate()
    }

    fun changePasswordVisibility() {
        passwordVisible.value = !passwordVisible.value
    }

    fun onCheckChange(){
        isChecked.value = !isChecked.value
    }
    fun saveRegister(){
        saveRegisterUseCase(true)
    }

    fun signUp() {
        if (!validationState.value.isValid) return

        viewModelScope.launch {
            authState.value = AuthState.Loading
            val result = signUpUseCase(email.value, password.value)
            authState.value = if (result.isSuccess) {
                saveRegister()
                AuthState.Success(R.string.ugurla)
            } else {
                AuthState.Error(R.string.naməlum_səhv_baş_verdi)
            }
        }
    }


    private fun validate() {
        validationState.value = validateAuthFieldsUseCase(email.value, password.value)
    }

}