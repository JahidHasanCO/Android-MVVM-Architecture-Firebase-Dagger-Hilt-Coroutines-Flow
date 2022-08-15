package dev.jahidhasanco.firebasemvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jahidhasanco.firebasemvvm.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoggedInViewModel
@Inject
constructor(
    private var authRepository: AuthRepository
) : ViewModel() {

    fun logOut() {
        viewModelScope.launch {
            authRepository.logOut()
        }
    }


}