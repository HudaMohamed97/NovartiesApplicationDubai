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



class NotificationsMessagingService : MessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.data != null) {
            val title = remoteMessage.data["title"]
            val message = remoteMessage.data["text"]
            val username = remoteMessage.data["username"]
        }

        val notificationTitle = remoteMessage.notification?.title
        val notificationBody = remoteMessage.notification?.body

        Log.i("hhhh", "Recieved" + remoteMessage.notification?.title)
        Log.i("hhhh", "Recieved" + remoteMessage.notification?.body)


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
            .setContentTitle(notificationTitle)
            .setContentText(notificationBody)
            .setSmallIcon(R.mipmap.icon_app)
            .setTimeoutAfter(3000)
            .setAutoCancel(true)
        notificationManager.notify(0, builder.build())
    }
}