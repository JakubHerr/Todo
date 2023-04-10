package io.github.jakubherr.todo.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jakubherr.todo.login.data.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {
    // TODO add UiState

    fun register(email: String, password: String) {
        viewModelScope.launch { repository.register(email, password) }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
        }
    }

    fun logout() = repository.logout()

    fun currentUser() = repository.currentUser()

    fun delete() {
        TODO("Add account deletion")
    }
}