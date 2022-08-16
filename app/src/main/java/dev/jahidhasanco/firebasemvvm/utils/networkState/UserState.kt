package dev.jahidhasanco.firebasemvvm.utils.networkState

import dev.jahidhasanco.firebasemvvm.data.model.User

data class UserState(
    val data: User? = null,
    val error: String = "",
    val isLoading: Boolean = false
)