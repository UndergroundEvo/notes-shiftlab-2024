package com.shift.notes.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

fun createNotificationChannel(context: Context) {
    val name = "Notes"
    val descriptionText = "notifications"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel("note_channel", name, importance).apply {
        description = descriptionText
    }
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)

}
