package com.example.todoapp.data.base

sealed class Resource<out T> {
    class Success<out T>(val data: T? = null): Resource<T>()
    class Error(val message: Event<String?>? = null): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}

open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandledOrReturnNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}