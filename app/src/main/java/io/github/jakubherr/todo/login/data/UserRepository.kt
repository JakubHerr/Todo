package io.github.jakubherr.todo.login.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.jakubherr.todo.login.model.User
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

interface UserRepository {
    suspend fun register(email: String, password: String): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    fun logout()
    fun currentUser(): User?
    //fun delete()

    companion object {
        operator fun invoke(): UserRepository = object : UserRepository {
            private val auth = Firebase.auth

            override suspend fun register(email: String, password: String): Result<User> {
                val result = try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    else return Result.failure(e)
                }

                return Result.success(
                    User(result!!.user!!.uid)
                )
            }

            override suspend fun login(email: String, password: String): Result<User> {
                val result = try {
                    auth.signInWithEmailAndPassword(email, password).await()
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    else return Result.failure(e)
                }

                return Result.success(
                    User(result!!.user!!.uid)
                )
            }

            override fun logout() = auth.signOut()

            override fun currentUser(): User? {
                val user = auth.currentUser ?: return null
                return User(user.uid)
            }
        }
    }
}