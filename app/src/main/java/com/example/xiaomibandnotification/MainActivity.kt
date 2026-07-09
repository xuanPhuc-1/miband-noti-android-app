package com.example.xiaomibandnotification

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.xiaomibandnotification.helper.NotificationHelper
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView
    private lateinit var txtLog: TextView

    private lateinit var btnEnablePermission: Button
    private lateinit var btnTest: Button

    private lateinit var switchMaps: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestNotificationPermission()
        // Ánh xạ các View từ XML
        txtStatus = findViewById(R.id.txtStatus)
        txtLog = findViewById(R.id.txtLog)

        btnEnablePermission = findViewById(R.id.btnEnablePermission)
        btnTest = findViewById(R.id.btnTest)

        switchMaps = findViewById(R.id.switchMaps)

        // Kiểm tra Notification Access
        updatePermissionStatus()

        // Nút mở màn hình cấp quyền
        btnEnablePermission.setOnClickListener {

            startActivity(
                Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            )

        }

        // Switch Google Maps
        switchMaps.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                txtLog.text = "Google Maps Forward Enabled"
            } else {
                txtLog.text = "Google Maps Forward Disabled"
            }

        }

        // Nút Test
        btnTest.setOnClickListener {

            NotificationHelper.showMapsNotification(
                this,
                "Arrow Test",
                """
                    ← Left
                    → Right
                    ↑ Straight
                    ↓ Back
                    
                    <-- Left ASCII
                    --> Right ASCII
                    ^ Straight ASCII
                    
                    LEFT
                    RIGHT
                    STRAIGHT
                    """.trimIndent()
                                )


        }

    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()

        updatePermissionStatus()
    }

    private fun updatePermissionStatus() {

        val enabledListeners = Settings.Secure.getString(
            contentResolver,
            "enabled_notification_listeners"
        )

        if (enabledListeners != null &&
            enabledListeners.contains(packageName)
        ) {

            txtStatus.text = "🟢 Notification Access Enabled"

        } else {

            txtStatus.text = "🔴 Notification Access Disabled"

        }

    }

}