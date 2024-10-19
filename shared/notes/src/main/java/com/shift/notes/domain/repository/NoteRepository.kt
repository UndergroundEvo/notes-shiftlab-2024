package com.shift.notes.domain.repository

import com.shift.notes.domain.model.Note

interface NoteRepository {
    suspend fun saveNote(note: Note): Result<Unit>

    suspend fun deleteNote(note: Note): Result<Unit>

    suspend fun getAllNotes(): Result<List<Note>>

    suspend fun searchNotes(query: String): Result<List<Note>>

    suspend fun filterNotesByTag(tag: String): Result<List<Note>>

    suspend fun getNoteById(id: Int): Result<Note?>

    suspend fun getNewNote(note: Note) : Result<Int?>

    suspend fun getLastNote() : Result<Int?>

    suspend fun getAllUniqueTags(): Result<List<String>>
}