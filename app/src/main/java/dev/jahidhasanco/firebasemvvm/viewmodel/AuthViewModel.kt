package dev.jahidhasanco.firebasemvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jahidhasanco.firebasemvvm.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private var authRepository: AuthRepository
) : ViewModel() {

    private var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()

    init {
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