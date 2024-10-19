package com.shift.notes.di

import com.shift.noteview.presentation.NoteViewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureNoteView = module {
    viewModel {
        NoteViewViewModel(get(), get(), get())
    }
}