package dev.jahidhasanco.firebasemvvm.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import dev.jahidhasanco.firebasemvvm.repository.AuthRepository

class AuthViewModel constructor(
    private val application: Application,
    private val authRepository: AuthRepository
) : ViewModel() {


}