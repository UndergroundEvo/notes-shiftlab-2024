package com.shift.notelist.presentation

import com.shift.notes.domain.model.Note

sealed interface NoteListState {
    data object Initial : NoteListState

    data object Loading : NoteListState

    data class Content(
        var notes: List<Note>,
        var selectedTags: List<String> = listOf("")
    ) : NoteListState

    data object Empty : NoteListState

    data class Error(val message: String) : NoteListState

}