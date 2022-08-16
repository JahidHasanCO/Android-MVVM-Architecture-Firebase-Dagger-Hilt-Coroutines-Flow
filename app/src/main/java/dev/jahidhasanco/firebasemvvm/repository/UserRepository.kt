package dev.jahidhasanco.firebasemvvm.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import dev.jahidhasanco.firebasemvvm.data.model.User
import dev.jahidhasanco.firebasemvvm.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor() {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStoreDatabase = FirebaseFirestore.getInstance()

    fun uploadUserData(user: User): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {
                fireStoreDatabase.collection("User")
                    .document(firebaseAuth.currentUser!!.uid)
                    .set(user).await()
                emit(Resource.Success(data = firebaseAuth.currentUser!!))
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        }
    }

    fun getUserData(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        if (firebaseAuth.currentUser != null) {
            try {
                val snapshot = fireStoreDatabase.collection("User")
                    .document(firebaseAuth.currentUser!!.uid).get().await()
                if (snapshot.exists()) {
                    val user: User? = snapshot.toObject(User::class.java)
                    emit(Resource.Success(data = user!!))
                }
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.localizedMessage ?: "Unknown Error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: "Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: ""))
            }
        }
    }

}