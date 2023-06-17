package io.github.jakubherr.todo.data

interface UserRepository {
    suspend fun register(email: String, password: String): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    fun logout()
    fun currentUser(): User?
    //fun delete()
}
