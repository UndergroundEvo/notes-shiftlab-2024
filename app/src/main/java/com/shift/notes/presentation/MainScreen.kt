package com.shift.notes.presentation

import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shift.notelist.NoteListRoute
import com.shift.notelist.ui.NoteListScreen
import com.shift.noteview.NoteViewRoute
import com.shift.noteview.ui.NoteViewScreen

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Surface {
        NavHost(
            navController = navController,
            startDestination = NoteListRoute,
            modifier = Modifier.imePadding()
        ) {
            composable<NoteListRoute> {
                NoteListScreen(noteSelect = { note ->
                    navController.navigate(NoteViewRoute(note))
                })
            }
            composable<NoteViewRoute> {
                val destination = it.toRoute<NoteViewRoute>().note
                NoteViewScreen(destination) { navController.navigate(NoteListRoute) }
            }
        }
    }
}