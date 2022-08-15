package dev.jahidhasanco.firebasemvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dev.jahidhasanco.firebasemvvm.repository.AuthRepository

class LoggedInViewModel constructor(
    application: Application,
    private var authRepository: AuthRepository
) : ViewModel() {

    private var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        authRepository = AuthRepository(application)
        userLiveData = authRepository.getUserLiveData()
        loggedOutLiveData = authRepository.getLoggedOutLiveData()
    }

    fun logOut() {
        authRepository.logOut()
    }

    fun getUserLiveData() = userLiveData

    fun getLoggedOutLiveData() = loggedOutLiveData

}