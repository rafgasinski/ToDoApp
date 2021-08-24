package com.example.todoapp.domain.di

import com.example.todoapp.domain.repositories.NoteRepository
import com.example.todoapp.domain.repositories.NoteRepositoryImpl
import com.example.todoapp.domain.usecases.GetNotesByCreationDateUseCase
import org.koin.dsl.module

fun getDomainModule() = module {
    factory { GetNotesByCreationDateUseCase(get()) }

    single<NoteRepository> { NoteRepositoryImpl(get()) }
}