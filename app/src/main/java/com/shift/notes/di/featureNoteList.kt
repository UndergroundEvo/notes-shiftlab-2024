package com.shift.notes.di

import com.shift.notelist.presentation.NoteListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureNoteList = module {
    viewModel {
        NoteListViewModel(get())
    }
}