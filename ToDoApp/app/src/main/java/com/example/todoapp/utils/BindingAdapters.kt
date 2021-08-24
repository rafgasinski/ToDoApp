package com.example.todoapp.utils

import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.todoapp.data.entities.Category
import java.time.LocalDate
import java.time.format.DateTimeFormatter


private val formatterFullDate = DateTimeFormatter.ofPattern("dd MMMM yyyy")
private val formatterDayMonth = DateTimeFormatter.ofPattern("dd MMMM")

@BindingAdapter("setTextAndVisibilityIfNotEmpty")
fun TextView.setTextAndVisibilityIfNotEmpty(text: String?) {
    text?.let {
        this.visibility = if(it.isNotEmpty()){
            this.text = it
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

@BindingAdapter("android:text")
fun TextView.text(category: Category?){
    this.text = category?.name ?: Category.Work.name
}

@BindingAdapter("android:text")
fun TextView.text(date: LocalDate?) {
    date?.let {
        this.text = if(date.year == LocalDate.now().year) {
            it.format(formatterDayMonth)
        } else {
            it.format(formatterFullDate)
        }
    }
}

@BindingAdapter("textFullDate")
fun TextView.textFullDate(date: LocalDate?) {
    this.text = date?.format(formatterFullDate) ?: LocalDate.now().format(formatterFullDate)
}