package com.example.todoapp.data.dao

import androidx.room.*
import com.example.todoapp.data.entities.Note
import com.example.todoapp.data.entities.NoteUpdate
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note): Long

    @Update(entity = Note::class)
    suspend fun updateNote(noteUpdate: NoteUpdate)

    @Query("DELETE from notes_table WHERE id =:id")
    suspend fun deleteNote(id: Int)

    @Query("SELECT * FROM notes_table WHERE id =:id")
    suspend fun getNote(id: Int): Note

    @Query("SELECT * FROM notes_table ORDER BY creationDate DESC")
    fun getNotesByCreationDate(): Flow<List<Note>>
}