package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder

import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.fcm.MessagingService
import android.R.attr.data
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class NotificationsMessagingService : MessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.i("recievedData", "Recieved" + remoteMessage.data["android.title"])
        Log.i("hhhh", "Recieved")

        val notificationBundle = remoteMessage.notification?.title
        val notificationbody = remoteMessage.notification?.body

        Log.i("recievedData", "Recieved" + remoteMessage.notification?.title)
        Log.i("recievedData", "Recieved" + remoteMessage.notification?.body)


        val deeplink = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.navigation)
            .setDestination(R.id.loginNav)
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
            .setSmallIcon(R.mipmap.icon_app)
            .setAutoCancel(true)
        notificationManager.notify(0, builder.build())
    }
}