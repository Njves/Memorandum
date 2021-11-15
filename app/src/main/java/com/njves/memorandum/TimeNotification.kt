package com.njves.memorandum

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.lang.IllegalStateException
import android.app.AlarmManager

import androidx.core.content.ContextCompat.getSystemService

import android.app.PendingIntent

import android.content.Intent
import androidx.core.content.ContextCompat
import java.util.*


const val CHANNEL_ID = "memorandum_notify"
class TimeNotification(private val context: Context) {
    companion object {
        private var instance: TimeNotification? = null

        fun getInstance(context: Context): TimeNotification {
            if(instance == null) {
                instance = TimeNotification(context)
            }
            return instance!!
        }
    }
    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Memorandum"
            val descriptionText = "Memorandum"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    fun sendNotification() {
        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notify = builder.build()
        with(NotificationManagerCompat.from(context)) {
            notify(1, notify)
        }
    }

    fun setAlarm() {
        val notifyTime: Calendar = GregorianCalendar()
        notifyTime.set(Calendar.HOUR_OF_DAY, 4)
        notifyTime.set(Calendar.MINUTE, 26)
        notifyTime.set(Calendar.SECOND, 0)

        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP,
            notifyTime.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}