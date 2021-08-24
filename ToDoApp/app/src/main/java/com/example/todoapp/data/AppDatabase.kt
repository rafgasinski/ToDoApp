package com.example.todoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.data.dao.NoteDao
import com.example.todoapp.data.entities.Note

@Database(entities = [Note::class], version = 1, exportSchema = false) @TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AppDatabase"
            ).fallbackToDestructiveMigration()
                .build()
    }
}