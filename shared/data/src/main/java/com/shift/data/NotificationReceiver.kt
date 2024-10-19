package com.shift.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

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
