package com.shift.notelist.ui

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.shift.notes.domain.model.Note

@Composable
fun NotesGrid(
    notes : List<Note>,
    noteSelect: (Int) -> Unit,
    noteDelete: (Note) -> Unit
){
    val currentNotes = remember { mutableStateListOf<Note>() }
    LaunchedEffect(notes) {
        currentNotes.clear() // Очищаем текущий список
        currentNotes.addAll(notes) // Добавляем новые элементы из notes
    }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2)
    ){
        items(currentNotes){ note ->
            NoteCard(
                note,
                noteSelect,
                noteDelete,
                {currentNotes.remove(note)}
            )
        }
    }
}