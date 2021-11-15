package com.njves.memorandum

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        TimeNotification.getInstance(context).sendNotification()
    }
}