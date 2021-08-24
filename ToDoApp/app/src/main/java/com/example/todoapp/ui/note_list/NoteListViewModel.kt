package com.example.todoapp.ui.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.base.Resource
import com.example.todoapp.data.entities.Note
import com.example.todoapp.domain.usecases.GetNotesByCreationDateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val getNotesByCreationDateUseCase: GetNotesByCreationDateUseCase,
): ViewModel() {
    private val _notesSortedByCreationDate: MutableStateFlow<Resource<List<Note>>> =
        MutableStateFlow(Resource.Loading)

    val notesSortedByCreationDate: StateFlow<Resource<List<Note>>> = _notesSortedByCreationDate

    init {
        getNotesByCreationDate()
    }

    private fun getNotesByCreationDate() {
        viewModelScope.launch {
            getNotesByCreationDateUseCase()
                .collect {
                    _notesSortedByCreationDate.value = it
                }
        }
    }
}