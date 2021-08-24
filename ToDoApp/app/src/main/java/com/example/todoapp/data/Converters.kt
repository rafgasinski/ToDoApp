package com.example.todoapp.data

import androidx.room.TypeConverter
import com.example.todoapp.data.entities.Category
import java.time.*

class Converters {
    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }

    @TypeConverter
    fun stringToCategory(category: String): Category {
        return Category.valueOf(category)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @TypeConverter
    fun fromTimestamp(date: Long): LocalDate {
        return LocalDate.ofEpochDay(date)
    }

}