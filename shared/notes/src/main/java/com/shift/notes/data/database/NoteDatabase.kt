package com.shift.notes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shift.notes.data.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
@TypeConverters(com.shift.notes.data.convert.TypeConverters::class)  // Подключаем наши конвертеры
abstract class NoteDatabase : RoomDatabase() {

    // Метод для получения DAO для доступа к данным
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        // Метод для создания или получения существующего экземпляра базы данных
        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .fallbackToDestructiveMigration() // Этот метод позволяет пересоздавать базу данных при изменении схемы
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}