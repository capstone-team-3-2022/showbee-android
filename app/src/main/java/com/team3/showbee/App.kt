package com.team3.showbee

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var prefs : SharedPreferences
    }

    override fun onCreate() {
        prefs = SharedPref.openSharedPrep(applicationContext)
        super.onCreate()
    }
}