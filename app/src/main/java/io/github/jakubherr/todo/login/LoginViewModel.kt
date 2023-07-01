package io.github.jakubherr.todo.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jakubherr.todo.data.UserRepository
import io.github.jakubherr.todo.data.model.User
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    // TODO add UiState

    fun register(email: String, password: String) {
        viewModelScope.launch { repository.register(email, password) }
    }

    suspend fun login(email: String, password: String): Result<User> = repository.login(email, password)

    fun logout() = repository.logout()

    fun currentUser() = repository.currentUser()

    fun delete() {
        TODO("Add account deletion")
    }
}
