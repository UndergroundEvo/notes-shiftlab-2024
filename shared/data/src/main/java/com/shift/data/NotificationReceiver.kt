package com.shift.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val noteId = intent.getIntExtra("noteId", -1)
        val activityClassName = intent.getStringExtra("activityClass")

        val activityClass = Class.forName(activityClassName)

        if (noteId != -1) {
            showNotification(context, noteId, activityClass)
        }
    }
}
