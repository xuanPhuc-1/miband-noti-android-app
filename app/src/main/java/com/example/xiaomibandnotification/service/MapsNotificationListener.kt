package com.example.xiaomibandnotification.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.example.xiaomibandnotification.helper.NotificationHelper
import com.example.xiaomibandnotification.parser.MapsParser

class MapsNotificationListener : NotificationListenerService() {

    companion object {
        private const val TAG = "MapsListener"
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d(TAG, "Notification Listener Connected")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)

        // Chỉ xử lý Google Maps
        if (sbn.packageName != "com.google.android.apps.maps") {
            return
        }

        val notification = sbn.notification
        val extras = notification.extras

        Log.d("MapsListener", "")
        Log.d("MapsListener", "========================================")
        Log.d("MapsListener", "Google Maps Notification")
        Log.d("MapsListener", "========================================")

        Log.d("MapsListener", "Package   : ${sbn.packageName}")
        Log.d("MapsListener", "ID        : ${sbn.id}")
        Log.d("MapsListener", "PostTime  : ${sbn.postTime}")
        Log.d("MapsListener", "Category  : ${notification.category}")
        Log.d("MapsListener", "Ticker    : ${notification.tickerText}")

        Log.d("MapsListener", "")
        Log.d("MapsListener", "----- Standard Fields -----")

        val navigation = MapsParser.parse(extras)

        Log.d(TAG, "Navigation")
        Log.d(TAG, "Title   : ${navigation.title}")
        Log.d(TAG, "Message : ${navigation.message}")
        Log.d(TAG, "ETA     : ${navigation.eta}")

        NotificationHelper.showMapsNotification(
            this,
            if (navigation.title.isBlank()) "Google Maps" else navigation.title,
            "${navigation.message}\n${navigation.eta}"
        )
    }
}