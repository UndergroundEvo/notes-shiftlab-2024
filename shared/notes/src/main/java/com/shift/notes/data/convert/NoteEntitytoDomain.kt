package com.shift.notes.data.convert

import com.shift.notes.data.entity.NoteEntity
import com.shift.notes.domain.model.Note

fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        tags = tags,
        imageUri = imageUri,
        timestamp = timestamp
    )
}