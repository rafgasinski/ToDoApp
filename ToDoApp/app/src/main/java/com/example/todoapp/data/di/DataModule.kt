package com.example.todoapp.data.di

import com.example.todoapp.data.AppDatabase
import com.example.todoapp.domain.repositories.NoteRepository
import com.example.todoapp.domain.repositories.NoteRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun getDataModule() = module {
    single { AppDatabase.buildDatabase(androidContext()) }

    factory { get<AppDatabase>().noteDao() }

    single<NoteRepository> { NoteRepositoryImpl(get()) }
}