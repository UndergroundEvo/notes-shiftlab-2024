package com.shift.noteview.ui.datetime

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun DateTimePicker(
    onTimestampSelected: (Long) -> Unit,
    windowOpen: (Boolean) -> Unit
) {
    val context = LocalContext.current

    var pickedDate by remember { mutableStateOf("") }
    var pickedTime by remember { mutableStateOf("") }
    var timestamp by remember { mutableStateOf(0L) }

    var showDatePicker by remember { mutableStateOf(true) }
    var showTimePicker by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            pickedTime = "$hourOfDay:$minute"

            timestamp = calendar.timeInMillis
            showTimePicker = false
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            pickedDate = "$dayOfMonth/${month + 1}/$year"

            showDatePicker = false
            showTimePicker = true
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    if (showDatePicker) { //legacy штука ＞︿＜
        datePickerDialog.show()
    }

    if (showTimePicker) {
        timePickerDialog.show()
    }

    if (pickedDate.isNotEmpty() && pickedTime.isNotEmpty()) {
        showTimePicker = false
        showDatePicker = false
        timePickerDialog.hide()
        datePickerDialog.hide()
        Log.i("showTimePicker","worked")
        onTimestampSelected(timestamp)
        windowOpen(false)
    }

}