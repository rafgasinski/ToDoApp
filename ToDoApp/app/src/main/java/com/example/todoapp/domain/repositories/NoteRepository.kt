package com.example.todoapp.domain.repositories

import com.example.todoapp.data.entities.Note
import com.example.todoapp.data.entities.NoteUpdate
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNote(note: Note)
    suspend fun updateNote(noteUpdate: NoteUpdate)
    suspend fun deleteNote(id: Int)
    suspend fun getNote(id: Int): Note
    suspend fun getNotesByCreationDate(): Flow<List<Note>>
}