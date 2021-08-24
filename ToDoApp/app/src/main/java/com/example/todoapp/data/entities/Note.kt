package com.example.todoapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "notes_table")
data class Note (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var text: String,
    var category: Category,
    var dueDate: LocalDate,
    val creationDate: LocalDate
)

data class NoteUpdate(
    val id: Int,
    val title: String,
    val text: String,
    val category: Category,
    val dueDate: LocalDate
)
