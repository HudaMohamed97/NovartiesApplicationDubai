package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder

import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.fcm.MessagingService

class NotificationsMessagingService : MessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.i("hhhh", "Recieved")
        val deeplink = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.navigation)
            .setDestination(R.id.Fragment)
            .createPendingIntent()

        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "1", "Deep Links", NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        val builder = NotificationCompat.Builder(
            this, "1"
        )
            .setContentTitle("Notification")
            .setContentText("Deep link to Android")
            .setContentIntent(deeplink)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
        notificationManager.notify(0, builder.build())
    }
}