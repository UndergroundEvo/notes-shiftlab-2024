package com.shift.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.shift.notes.data.notification.createNotificationChannel
import com.shift.notes.presentation.MainScreen
import com.shift.notes.ui.theme.NotesTheme

/**
 *  Идеи для реализации
 *  * Для тегов добавить генерациях цветов
 *
 *
 * */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)
        enableEdgeToEdge()
        setContent {
            NotesTheme {
                MainScreen()
            }
        }
    }
}