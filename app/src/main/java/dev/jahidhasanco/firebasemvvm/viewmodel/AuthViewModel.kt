package dev.jahidhasanco.firebasemvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jahidhasanco.firebasemvvm.repository.AuthRepository
import dev.jahidhasanco.firebasemvvm.utils.Resource
import dev.jahidhasanco.firebasemvvm.utils.networkState.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private var authRepository: AuthRepository
) : ViewModel() {


    private val _userList = MutableStateFlow(AuthState())
    val user: StateFlow<AuthState> = _userList

    fun login(email: String, password: String) {
        authRepository.login(email, password).onEach {
            when (it) {
                is Resource.Loading -> {
                    _userList.value = AuthState(isLoading = true)
                }
                is Resource.Error -> {
                    _userList.value = AuthState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _userList.value = AuthState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun register(email: String, password: String) {
        authRepository.register(email, password).onEach {
            when (it) {
                is Resource.Loading -> {
                    _userList.value = AuthState(isLoading = true)
                }
                is Resource.Error -> {
                    _userList.value = AuthState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _userList.value = AuthState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loggedUser() {

        authRepository.getLoggedUser().onEach {
            when (it) {
                is Resource.Loading -> {
                    _userList.value = AuthState(isLoading = true)
                }
                is Resource.Error -> {
                    _userList.value = AuthState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _userList.value = AuthState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}