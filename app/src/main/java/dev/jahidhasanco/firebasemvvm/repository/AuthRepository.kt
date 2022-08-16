package dev.jahidhasanco.firebasemvvm.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dev.jahidhasanco.firebasemvvm.utils.displayToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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

    suspend fun register(email: String, password: String) {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            userLiveData.postValue(result.user)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                appContext.applicationContext.displayToast("Registration Failure ${e.message}")
            }
        }
    }

    suspend fun login(email: String, password: String) {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            userLiveData.postValue(result.user)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                appContext.applicationContext.displayToast("Login Failure ${e.message}")
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