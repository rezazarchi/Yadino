package com.rahim.data.repository.note

import com.rahim.data.modle.note.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNote(noteModel: NoteModel)
    suspend fun updateNote(noteModel: NoteModel)
    suspend fun deleteNote(noteModel: NoteModel)

    fun getNotes(): Flow<List<NoteModel>>
}