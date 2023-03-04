package io.github.jakubherr.todo

import android.app.Application
import io.github.jakubherr.todo.login.UserViewModel
import io.github.jakubherr.todo.login.data.UserRepository
import io.github.jakubherr.todo.tasks.TaskViewModel
import io.github.jakubherr.todo.tasks.data.TaskRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TodoApplication: Application() {
    private val appModule = module {
        single { TaskRepository() }
        single { UserRepository() }
        viewModel { TaskViewModel(get()) }
        viewModel { UserViewModel(get()) }
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