package dev.jahidhasanco.firebasemvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dev.jahidhasanco.firebasemvvm.repository.AuthRepository

class AuthViewModel constructor(
    application: Application,
    private var authRepository: AuthRepository
) : ViewModel() {

    private var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()

    init {
        authRepository = AuthRepository(application)
        userLiveData = authRepository.getUserLiveData()
    }

    fun login(email: String, password: String) {
        authRepository.login(email, password)
    }

    fun register(email: String, password: String) {
        authRepository.register(email, password)
    }

    fun getUserLiveData() = userLiveData
}