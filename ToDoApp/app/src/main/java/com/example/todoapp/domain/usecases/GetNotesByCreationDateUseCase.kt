package com.example.todoapp.domain.usecases

import com.example.todoapp.data.base.FlowUseCase
import com.example.todoapp.data.base.Resource
import com.example.todoapp.data.entities.Note
import com.example.todoapp.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesByCreationDateUseCase(
    private val noteRepository: NoteRepository
):  FlowUseCase<Nothing?, List<Note>>() {

    override suspend fun execute(parameters: Nothing?): Flow<Resource<List<Note>>> {
        return noteRepository.getNotesByCreationDate().map { Resource.Success(it) }
    }
}