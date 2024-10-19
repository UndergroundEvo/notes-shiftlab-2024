package com.shift.notelist.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.shift.notelist.R
import com.shift.notelist.presentation.NoteListState
import com.shift.notelist.presentation.NoteListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    noteSelect: (Int) -> Unit
) {
    val viewModel = koinViewModel<NoteListViewModel>()
    val state by viewModel.state.collectAsState()
    val scope = viewModel.viewModelScope

    var searchState by remember { mutableStateOf("") }
    var searchModal by remember { mutableStateOf(false) }

    if (searchModal) {
        BasicAlertDialog(
            onDismissRequest = { searchModal = false }
        ) {
            Card(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.search_title),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    TextField(
                        label = { },
                        value = searchState,
                        onValueChange = {
                            searchState = it
                        },
                        maxLines = 2
                    )
                    TextButton(
                        onClick = {
                            viewModel.searchNotes(searchState)
                            searchModal = false
                        },
                    ) {
                        Text(stringResource(R.string.search_word))
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getNotes()
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    if (searchState.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.getNotes()
                                searchState = ""
                                searchModal = false
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = stringResource(R.string.check_multi)
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            searchModal = true
                        }) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = stringResource(R.string.check_multi)
                            )
                        }
                    }

                    IconButton(onClick = {}, enabled = false) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = stringResource(R.string.create_regular),
                        )
                    }
                    IconButton(onClick = {}, enabled = false) {
                        Icon(
                            imageVector = ImageVector.vectorResource(com.shift.notes.R.drawable.filled_image_icon),
                            contentDescription = stringResource(R.string.create_image),
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                val newNoteId = viewModel.createNewNote()
                                Log.i("NoteListScreen", "iddd = $newNoteId")
                                noteSelect(newNoteId)
                            }
                        },
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, stringResource(R.string.create_word))
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (state) {
                is NoteListState.Content -> {
                    val notes = (state as NoteListState.Content).notes
                    NotesGrid(
                        notes,
                        noteSelect,
                        { note -> viewModel.deleteNote(note) }
                    )
                }

                NoteListState.Empty -> {
                    EmptyScreen()
                }

                is NoteListState.Error -> {}
                NoteListState.Initial -> {}
                NoteListState.Loading -> {
                    LoadingScreen()
                }
            }

        }
    }

}