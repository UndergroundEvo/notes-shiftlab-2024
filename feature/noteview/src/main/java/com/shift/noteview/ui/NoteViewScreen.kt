package com.shift.noteview.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.shift.notes.domain.model.Note
import com.shift.noteview.R
import com.shift.noteview.presentation.NoteViewState
import com.shift.noteview.presentation.NoteViewViewModel
import com.shift.noteview.ui.datetime.DateTimePicker
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun NoteViewScreen(
    noteId: Int,
    onPopStackBack: () -> Unit
) {
    val viewModel = koinViewModel<NoteViewViewModel>()
    val state by viewModel.state.collectAsState()
    viewModel.getNote(noteId) // ＞︿＜

    when (state) {
        is NoteViewState.Content -> {
            val note = (state as NoteViewState.Content).note

            var titleNote by remember { mutableStateOf(note.title) }
            var contentNote by remember { mutableStateOf(note.content) }
            var timestampNote by remember { mutableStateOf(note.timestamp) }
            val tagsNote by remember { mutableStateOf(note.tags.size) }
            val openTagsDialog = remember { mutableStateOf(false) }
            var openTagsDialogString by remember { mutableStateOf("") }
            var timestampDialog by remember { mutableStateOf(false) }

            DisposableEffect(Unit) {
                onDispose {
                    note.content = contentNote
                    note.title = titleNote
                    viewModel.saveNote(note)
                }
            }
            if (timestampDialog){
                BasicAlertDialog(
                    onDismissRequest = { timestampDialog = false }
                ) {
                    DateTimePicker(
                        onTimestampSelected = { timestamp ->
                            timestampNote = timestamp
                        },
                        windowOpen = {timestampDialog ->
                            timestampDialog.not()
                        }
                    )
                }
            }
            if (openTagsDialog.value) {
                BasicAlertDialog(
                    onDismissRequest = {
                        openTagsDialog.value = false
                    },
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
                                text = stringResource(R.string.add_tag),
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.padding(6.dp))
                            TextField(
                                label = { Text(text = stringResource(R.string.tag_word)) },
                                value = openTagsDialogString,
                                onValueChange = {
                                    openTagsDialogString = it
                                },
                                maxLines = 2
                            )
                            TextButton(
                                onClick = {
                                    val newTags = note.tags + openTagsDialogString
                                    note.tags = newTags

                                    viewModel.saveNote(note)

                                    openTagsDialogString = ""
                                    openTagsDialog.value = false
                                },
                            ) {
                                Text(stringResource(R.string.ready_note_word))
                            }
                        }
                    }

                }
            }

            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(stringResource(R.string.note_edit)) })
                },
                bottomBar = {
                    BottomAppBar(
                        actions = {
                            IconButton(onClick = {
                                timestampDialog = true
                            }) {
                                Icon(
                                    Icons.Filled.Notifications,
                                    contentDescription = stringResource(R.string.notification_word),
                                )
                            }
                            IconButton(onClick = {}, enabled = false) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(com.shift.notes.R.drawable.filled_image_icon),
                                    contentDescription = stringResource(R.string.image_word),
                                )
                            }
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    //note.tags = tagsNote.toList()
                                    note.content = contentNote
                                    note.title = titleNote
                                    viewModel.saveNote(note)
                                    onPopStackBack()
                                },
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                            ) {
                                Icon(Icons.Filled.Check, stringResource(R.string.ready_note_word))
                            }
                        }
                    )
                }
            ) { paddingValue ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValue)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp).verticalScroll(rememberScrollState()),
                    ) {
                        FlowRow(
                            maxLines = 2,
                        ) {
                            IconButton(
                                onClick = {
                                    openTagsDialog.value = true
                                }
                            ) {
                                Icon(Icons.Filled.Add, "")
                            }
                            repeat(tagsNote) { index ->
                                ElevatedCard(
                                    colors = CardDefaults.cardColors(
                                        containerColor = tagsColor(note.tags[index])
                                    ),
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .combinedClickable(
                                            onClick = {},
                                            onLongClick = {
                                                val newList = note.tags.toMutableList()
                                                newList.remove(note.tags[index])
                                                note.tags = newList.toList()
                                                viewModel.saveNote(note)
                                            }
                                        ),
                                ){
                                    Text(
                                        modifier = Modifier.padding(6.dp),
                                        text = note.tags[index]
                                    )
                                }
                            }


                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = stringResource(R.string.title_word)) },
                            value = titleNote,
                            onValueChange = { titleNote = it },
                            maxLines = 2,
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                            )
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                                //.heightIn(600.dp, 1000.dp),
                            label = { Text(text = stringResource(R.string.content_word)) },
                            value = contentNote,
                            onValueChange = { contentNote = it },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                                focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                            )
                        )
                    }

                }
            }

        }

        is NoteViewState.Error -> {
            NoteViewError()
        }

        NoteViewState.Initial -> {}

        NoteViewState.Loading -> {
            LoadingScreen()
        }
    }


}