package com.shift.notes

import android.app.Application
import com.shift.notes.di.databaseModule
import com.shift.notes.di.featureNoteList
import com.shift.notes.di.featureNoteView
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@NotesApp)
            modules(listOf(databaseModule, featureNoteList, featureNoteView))

        }
    }
}