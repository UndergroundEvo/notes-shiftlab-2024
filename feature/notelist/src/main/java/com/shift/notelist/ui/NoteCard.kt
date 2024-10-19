package com.shift.notelist.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shift.notelist.R
import com.shift.notes.domain.model.Note

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    note: Note,
    noteSelect: (Int) -> Unit,
    noteDelete: (Note) -> Unit,
    onDelete: () -> Unit
) {
    val openAlertDialog = remember { mutableStateOf(false) }

    if (openAlertDialog.value) {
        BasicAlertDialog(
            onDismissRequest = {
                openAlertDialog.value = false
            },
        ) {
                Card(
                    Modifier.padding(16.dp).fillMaxWidth().height(200.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.delete_note_alert) + """ """" +
                                    note.title + """" """ + stringResource(R.string.delete_note_alert_continue),
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.padding(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = {
                                    noteDelete(note)
                                    onDelete()
                                    openAlertDialog.value = false
                                },
                            ) {
                                Text(stringResource(R.string.delete_dialog_word))
                            }
                            Spacer(modifier = Modifier.padding(8.dp))
                            TextButton(
                                onClick = {
                                    openAlertDialog.value = false
                                },
                            ) {
                                Text(stringResource(R.string.cancel_dialog_word))
                            }
                        }

                    }
                }

        }
    }

    OutlinedCard(
        modifier = Modifier
            .heightIn(150.dp, 300.dp)
            .padding(4.dp)
            .combinedClickable(
                onClick = {
                    noteSelect(note.id)
                },
                onLongClick = {
                    openAlertDialog.value = true
                }
            )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = note.title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp
            )
            Spacer(Modifier.padding(8.dp))
            Text(
                text = note.content,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}