package com.shift.notes.data.repository

import com.shift.notes.data.convert.toDomain
import com.shift.notes.data.convert.toEntity
import com.shift.notes.data.database.NoteDao
import com.shift.notes.domain.model.Note
import com.shift.notes.domain.repository.NoteRepository

/*тут не совсем эффективная работа с бд,
надо везде работать по note.id а по самой Note*/

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    override suspend fun saveNote(note: Note): Result<Unit> {
        return try {
            noteDao.insert(note.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteNote(note: Note): Result<Unit> {
        return try {
            noteDao.delete(note.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllNotes(): Result<List<Note>> {
        return try {
            val notes = noteDao.getAllNotes().map { it.toDomain() }
            Result.success(notes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchNotes(query: String): Result<List<Note>> {
        return try {
            val notes = noteDao.searchNotes(query).map { it.toDomain() }
            Result.success(notes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun filterNotesByTag(tag: String): Result<List<Note>> {
        return try {
            val notes = noteDao.filterNotesByTag(tag).map { it.toDomain() }
            Result.success(notes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNoteById(id: Int): Result<Note?> {
        return try {
            val noteEntity = noteDao.getNoteById(id)
            if (noteEntity != null) {
                Result.success(noteEntity.toDomain())
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNewNote(note : Note): Result<Int?> {
        return try {
            val noteEntity = note.toEntity()
            noteDao.insert(noteEntity)
            val newNoteId = noteDao.getLastIdNote()
            Result.success(newNoteId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLastNote(): Result<Int?> {
        return try {
            val lastNote = noteDao.getLastNote()
            Result.success(lastNote.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllUniqueTags(): Result<List<String>> {
        return try {
            val tagsList = noteDao.getAllTags()
            val allTags = tagsList.flatMap { it.split(",") }.map { it.trim() }
            val uniqueTags = allTags.distinct()

            Result.success(uniqueTags)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}