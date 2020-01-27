package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.pusher.pushnotifications.PushNotifications

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PushNotifications.start(applicationContext, "e3261a40-f1c1-4a9d-b568-4da52ee960ec")
        PushNotifications.addDeviceInterest("hello")
        // applicationClass = ApplicationClass.getApplication()
        //  createNotification()
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
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
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
}
