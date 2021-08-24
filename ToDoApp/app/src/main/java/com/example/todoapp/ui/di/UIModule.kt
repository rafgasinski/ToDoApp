package com.example.todoapp.ui.di

import com.example.todoapp.ui.note_details.NoteDetailsViewModel
import com.example.todoapp.ui.note_list.NoteListViewModel
import com.example.todoapp.utils.PreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getUIModules() = module {
    viewModel {
        NoteListViewModel(get())
    }

    viewModel {
        NoteDetailsViewModel(get())
    }

    single {
        PreferencesManager(androidContext())
    }
}