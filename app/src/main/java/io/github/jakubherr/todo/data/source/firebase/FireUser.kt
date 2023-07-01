package io.github.jakubherr.todo.data.source.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.jakubherr.todo.data.model.User
import io.github.jakubherr.todo.data.UserRepository
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class FireUser : UserRepository {
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
