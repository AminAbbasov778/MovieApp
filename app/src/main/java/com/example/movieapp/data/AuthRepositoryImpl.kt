package com.example.movieapp.data

import android.util.Log
import com.example.movieapp.domain.interfaces.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(val firebaseAuth: FirebaseAuth) : AuthRepository {


    override suspend fun signIn(
        email: String,
        password: String,
    ): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)

        } catch (e: Exception) {
            Log.e("yoxla1", e.localizedMessage.toString(), )
            Result.failure(e)
        }
    }


    override suspend fun signUp(
        email: String,
        password: String,
    ): Result<Unit> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)

        } catch (e: Exception) {
            Log.e("yoxla1", e.localizedMessage.toString(), )
            Result.failure(e)

        }
    }
}
