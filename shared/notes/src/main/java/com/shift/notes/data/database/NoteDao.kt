package com.shift.notes.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shift.notes.data.entity.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%'")
    suspend fun searchNotes(query: String): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE tags LIKE '%' || :tag || '%'")
    suspend fun filterNotesByTag(tag: String): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Query("SELECT MAX(id) FROM notes")
    suspend fun getLastIdNote(): Int?

    @Query("SELECT * FROM notes ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastNote(): NoteEntity

    @Query("SELECT tags FROM notes")
    suspend fun getAllTags(): List<String>
}