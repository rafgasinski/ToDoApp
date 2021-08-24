package com.example.todoapp.domain.repositories

import com.example.todoapp.data.dao.NoteDao
import com.example.todoapp.data.entities.Note
import com.example.todoapp.data.entities.NoteUpdate
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao): NoteRepository {
    override suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    override suspend fun updateNote(noteUpdate: NoteUpdate) {
        noteDao.updateNote(noteUpdate)
    }

    override suspend fun deleteNote(id: Int) {
        noteDao.deleteNote(id)
    }

    override suspend fun getNote(id: Int): Note {
        return noteDao.getNote(id)
    }

    override suspend fun getNotesByCreationDate(): Flow<List<Note>> {
        return noteDao.getNotesByCreationDate()
    }
}