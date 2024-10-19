package com.shift.noteview.presentation

import com.shift.notes.domain.model.Note

sealed interface NoteViewState {
    data object Initial : NoteViewState

    data object Loading : NoteViewState

    data class Content(
        var note: Note
    ) : NoteViewState

    data class Error(val message: String) : NoteViewState
}