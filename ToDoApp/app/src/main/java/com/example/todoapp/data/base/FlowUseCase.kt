package com.example.todoapp.data.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class FlowUseCase<in P, R> {
    suspend operator fun invoke(parameters: P? = null): Flow<Resource<R>> = execute(parameters)
        .catch { e ->
            emit(
                Resource.Error(
                    Event(e.localizedMessage)
                )
            )
        }

    protected abstract suspend fun execute(parameters: P? = null): Flow<Resource<R>>
}