package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.di.getDataModule
import com.example.todoapp.domain.di.getDomainModule
import com.example.todoapp.ui.di.getUIModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val list = listOf(
            getDataModule(),
            getDomainModule(),
            getUIModules()
        )

        startKoin {
            modules(list)
            androidContext(this@App)
        }
    }
}