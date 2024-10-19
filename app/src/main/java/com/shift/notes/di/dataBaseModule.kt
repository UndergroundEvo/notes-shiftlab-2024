package com.shift.notes.di

import androidx.room.Room
import com.shift.notes.data.database.NoteDatabase
import com.shift.notes.data.repository.NoteRepositoryImpl
import com.shift.notes.domain.repository.NoteRepository
import com.shift.notes.domain.usecase.NotificationUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NoteDatabase::class.java,
            "note_database"
        ).build()
    }
    single<NoteRepository> { NoteRepositoryImpl(get()) }
    single { get<NoteDatabase>().noteDao() }
    factory { NotificationUseCase(get()) }
}