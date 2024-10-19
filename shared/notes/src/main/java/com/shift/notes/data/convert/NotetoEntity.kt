package com.shift.notes.data.convert

import com.shift.notes.data.entity.NoteEntity
import com.shift.notes.domain.model.Note

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        tags = tags,
        imageUri = imageUri,
        timestamp = timestamp
    )
}