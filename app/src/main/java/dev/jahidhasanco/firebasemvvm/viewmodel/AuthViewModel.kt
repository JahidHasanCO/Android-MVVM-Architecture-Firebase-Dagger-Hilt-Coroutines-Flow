package dev.jahidhasanco.firebasemvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import dev.jahidhasanco.firebasemvvm.repository.AuthRepository

class AuthViewModel constructor(
    application: Application
) : AndroidViewModel(application) {

    private var authRepository: AuthRepository
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