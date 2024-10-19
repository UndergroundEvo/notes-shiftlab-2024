package com.shift.notelist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shift.notes.domain.model.Note
import com.shift.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val repository: NoteRepository
) : ViewModel() {
    private val _state = MutableStateFlow<NoteListState>(NoteListState.Initial)
    val state: StateFlow<NoteListState> = _state.asStateFlow()

    fun searchNotes(title : String) {
        viewModelScope.launch {
            try {
                _state.value = NoteListState.Loading
                val response = repository.searchNotes(title)
                response.fold(
                    onSuccess = { notes ->
                        if (notes.isNotEmpty()) {
                            _state.value = NoteListState.Content(notes)
                        } else {
                            _state.value = NoteListState.Empty
                        }
                    },
                    onFailure = { error ->
                        _state.value = NoteListState.Error(error.message ?: "Ошибка базы данных") //todo
                    }
                )
            } catch (e: Exception) {
                _state.value = NoteListState.Error(e.localizedMessage ?: "Неизвестная ошибка")
            }
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            try {
                _state.value = NoteListState.Loading
                val response = repository.getAllNotes()
                response.fold(
                    onSuccess = { notes ->
                        if (notes.isNotEmpty()) {
                            _state.value = NoteListState.Content(notes)
                        } else {
                            _state.value = NoteListState.Empty
                        }
                    },
                    onFailure = { error ->
                        _state.value = NoteListState.Error(error.message ?: "Ошибка базы данных") //todo
                    }
                )
            } catch (e: Exception) {
                _state.value = NoteListState.Error(e.localizedMessage ?: "Неизвестная ошибка")
            }
        }
    }

    suspend fun createNewNote(): Int {
        val newNote = Note(
            id = 0,
            title = "New Note",
            content = "",
            tags = listOf(),
            imageUri = null,
            timestamp = System.currentTimeMillis()
        )
        return try {
            val result = repository.getNewNote(newNote)
            result.onSuccess {
                repository.getLastNote().getOrNull() ?: 0
            }.getOrNull() ?: 0
        } catch (e: Exception) {
            404
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

}