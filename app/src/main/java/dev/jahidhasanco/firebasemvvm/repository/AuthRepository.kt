package dev.jahidhasanco.firebasemvvm.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dev.jahidhasanco.firebasemvvm.utils.displayToast

class AuthRepository constructor(private var application: Application) {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private var loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }
    }

    fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    application.applicationContext.displayToast("Registration Failure ${it.exception!!.message}")
                }
            }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    application.applicationContext.displayToast("Login Failure ${it.exception!!.message}")
                }
            }
    }

    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }

    fun getUserLiveData() = userLiveData

    fun getLoggedOutLiveData() = loggedOutLiveData
}