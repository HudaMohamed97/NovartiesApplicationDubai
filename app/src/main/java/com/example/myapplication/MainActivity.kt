package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.PushNotificationReceivedListener
import com.pusher.pushnotifications.PushNotifications
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var loginPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("hhhh", "init")
        loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val saveLogin = loginPreferences.getBoolean("saveLogin", false)

        PushNotifications.start(applicationContext, "e3261a40-f1c1-4a9d-b568-4da52ee960ec")
        PushNotifications.addDeviceInterest("debug-hello")

        val navHostFragment = nav_host_fragment as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        var navGraph = graphInflater.inflate(R.navigation.navigation)
        var navController = navHostFragment.navController

        if (!saveLogin) {
            navGraph.startDestination = R.id.loginFragment
            navController.graph = navGraph
        } else {
            navGraph.startDestination = R.id.navigation
            navController.graph = navGraph
        }

    }

    private fun getDeviceTokenId() {
        try {
            val instanceID = InstanceID.getInstance(this)
            val token = instanceID.getToken(
                getString(R.string.gcm_defaultSenderId),
                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null
            )
            Log.i("hhhhh", "GCM Registration Token: $token")
        } catch (e: Exception) {
            Log.i("hhhhh", "Failed to complete token refresh", e)
        }

    }

    private fun createNotification() {
        var args = Bundle()
        args.putString("arg", "hello")
        val deeplink = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.navigation)
            .setDestination(R.id.Fragment)
            .setArguments(args)
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
            .setContentTitle("Navigation")
            .setContentText("Deep link to Android")
            .setContentIntent(deeplink)
            .setSmallIcon(R.drawable.ic_comment_btn)
            .setAutoCancel(true)
        notificationManager.notify(0, builder.build())

    }


    /* val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         notificationManager.createNotificationChannel(
             NotificationChannel(
             channelId, "Deep Links", NotificationManager.IMPORTANCE_HIGH)
         )
     }
     val builder = NotificationCompat.Builder(
         context!!, channelId)
         .setContentTitle("Navigation")
         .setContentText("Deep link to Android")
         .setSmallIcon(R.drawable.ic_menu_camera)
         .setContentIntent(deeplink)
         .setAutoCancel(true)
     notificationManager.notify(0, builder.build())
     */


    /* private fun launchBarCodeActivity() {
         val launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false)
         startActivityForResult(launchIntent, ACTIVITYREQUEST)
     }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         if (resultCode != Activity.RESULT_OK) {
             Toast.makeText(this, "error in  scanning", Toast.LENGTH_SHORT).show()
             return
         }

         if (requestCode == ACTIVITYREQUEST && data != null) {
             val barcode =
                 data.getParcelableExtra<Barcode>(BarcodeReaderActivity.KEY_CAPTURED_BARCODE)
             Toast.makeText(this, barcode!!.rawValue, Toast.LENGTH_SHORT).show()
             text.setText(barcode.rawValue)
         }

     }*/

    override fun onResume() {
        super.onResume()
        PushNotifications.setOnMessageReceivedListenerForVisibleActivity(
            this,
            object : PushNotificationReceivedListener {
                override fun onMessageReceived(remoteMessage: RemoteMessage) {
                    val messagePayload = remoteMessage.data["inAppNotificationMessage"]
                    if (messagePayload == null) {
                        // Message payload was not set for this notification
                        Log.i("hhhhhh", "Payload was missing")
                    } else {
                        Log.i("hhhhhh", messagePayload)
                        // Now update the UI based on your message payload!
                    }
                }
            })
    }
}
