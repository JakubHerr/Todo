package io.github.jakubherr.todo

import android.app.Application
import io.github.jakubherr.todo.data.ProjectRepository
import io.github.jakubherr.todo.login.LoginViewModel
import io.github.jakubherr.todo.data.UserRepository
import io.github.jakubherr.todo.tasks.TaskViewModel
import io.github.jakubherr.todo.data.TaskRepository
import io.github.jakubherr.todo.data.source.firebase.FireProject
import io.github.jakubherr.todo.data.source.firebase.FireTask
import io.github.jakubherr.todo.data.source.firebase.FireUser
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TodoApplication: Application() {
    private val appModule = module {
        single<TaskRepository> { FireTask() }
        single<UserRepository> { FireUser() }
        single<ProjectRepository> { FireProject() }
        viewModel { TaskViewModel(get()) }
        viewModel { LoginViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@TodoApplication)
            modules(appModule)
        }
    }
}