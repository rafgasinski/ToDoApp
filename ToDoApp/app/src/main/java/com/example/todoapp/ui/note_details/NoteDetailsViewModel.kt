package com.example.todoapp.ui.note_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.entities.Note
import com.example.todoapp.data.entities.NoteUpdate
import com.example.todoapp.domain.repositories.NoteRepository
import kotlinx.coroutines.launch

class NoteDetailsViewModel(private val noteRepository: NoteRepository): ViewModel() {

    private val _note: MutableLiveData<Note> = MutableLiveData()
    val note: LiveData<Note> = _note

    fun getNote(id: Int) {
        viewModelScope.launch {
            _note.value = noteRepository.getNote(id)
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository.addNote(note)
        }
    }

    fun updateNote(noteUpdate: NoteUpdate) {
        viewModelScope.launch {
            noteRepository.updateNote(noteUpdate)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            noteRepository.deleteNote(id)
        }
    }
}