package dev.jahidhasanco.firebasemvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jahidhasanco.firebasemvvm.data.model.User
import dev.jahidhasanco.firebasemvvm.repository.UserRepository
import dev.jahidhasanco.firebasemvvm.utils.Resource
import dev.jahidhasanco.firebasemvvm.utils.networkState.AuthState
import dev.jahidhasanco.firebasemvvm.utils.networkState.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel
@Inject
constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _userList = MutableStateFlow(AuthState())
    val user: StateFlow<AuthState> = _userList

    private val _userData = MutableStateFlow(UserState())
    val userData: StateFlow<UserState> = _userData

    fun uploadUserData(user: User) {
        userRepository.uploadUserData(user).onEach {
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

    fun getUserData() {
        userRepository.getUserData().onEach {
            when (it) {
                is Resource.Loading -> {
                    _userData.value = UserState(isLoading = true)
                }
                is Resource.Error -> {
                    _userData.value = UserState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _userData.value = UserState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}