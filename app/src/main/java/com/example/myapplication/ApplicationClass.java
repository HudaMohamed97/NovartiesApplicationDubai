package com.example.myapplication;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.onesignal.OneSignal;

public class ApplicationClass extends Application {
    private static ApplicationClass applicationClass;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public ApplicationClass() {
        applicationClass = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("hhhh","done jjjjhjkhkj init");
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        Log.i("hhhh","done init");
        applicationClass = this;

    }

    public static synchronized ApplicationClass getApplication() {
        return applicationClass;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
