package com.shift.noteview.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shift.notes.domain.model.Note
import com.shift.notes.domain.repository.NoteRepository
import com.shift.notes.domain.usecase.NotificationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.Timestamp

class NoteViewViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NoteRepository,
    private val notificationUseCase: NotificationUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<NoteViewState>(NoteViewState.Initial)
    val state: StateFlow<NoteViewState> = _state.asStateFlow()

    fun setnotification(noteId: Int, timestamp: Long){
        notificationUseCase.scheduleNotification(noteId,timestamp)
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            try {
                val response = repository.saveNote(note)
                response.fold(
                    onSuccess = {},
                    onFailure = { error ->
                        _state.value =
                            NoteViewState.Error(error.message ?: "Ошибка базы данных") //todo
                    }
                )
            } catch (e: Exception) {
                _state.value = NoteViewState.Error(e.localizedMessage ?: "Неизвестная ошибка")
            }
        }
    }

    fun getNote(noteId: Int) {
        viewModelScope.launch {
            try {
                //_state.value = NoteViewState.Loading
                val response = repository.getNoteById(noteId)
                response.fold(
                    onSuccess = { value ->
                        if (value != null) {
                            _state.value = NoteViewState.Content(value)
                        } else {
                            _state.value = NoteViewState.Error("Ошибка БД")
                        }
                    },
                    onFailure = { error ->
                        _state.value =
                            NoteViewState.Error(error.message ?: "Ошибка базы данных") //todo
                    }
                )
            } catch (e: Exception) {
                _state.value = NoteViewState.Error(e.localizedMessage ?: "Неизвестная ошибка")
            }
        }
    }
}