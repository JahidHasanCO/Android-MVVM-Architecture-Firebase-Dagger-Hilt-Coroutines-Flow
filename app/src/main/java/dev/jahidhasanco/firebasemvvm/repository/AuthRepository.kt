package dev.jahidhasanco.firebasemvvm.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dev.jahidhasanco.firebasemvvm.utils.displayToast
import javax.inject.Inject

class AuthRepository
@Inject
constructor(private var appContext: Context) {

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
                    appContext.applicationContext.displayToast("Registration Failure ${it.exception!!.message}")
                }
            }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                } else {
                    appContext.applicationContext.displayToast("Login Failure ${it.exception!!.message}")
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