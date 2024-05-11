package com.acuon.moengage

import android.app.Application
import com.acuon.moengage.data.pref.Preferences
import com.acuon.moengage.fcm.createNotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoEngageApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Preferences.init(this)
    }
}