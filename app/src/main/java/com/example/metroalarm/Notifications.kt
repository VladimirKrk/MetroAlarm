package com.example.metroalarm.com.example.metroalarm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.widget.Toast

class NotificationHelper(private val context: Context) {

    private val channelId = "heads_up_channel"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Heads-Up Notifications"
            val descriptionText = "Notifications with vibration"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 200, 500)
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, message: String) {
        if (checkNotificationPermission()) {
            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(0, 500, 200, 500))
                .build()

            NotificationManagerCompat.from(context).notify(1, notification)
        } else {
            Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show()
        }
    }
    fun showHeadsUpNotification(title: String, message: String) {
        val context = context // Use your activity or application context

        // Define the notification channel ID (should match the one in NotificationHelper)
        val channelId = "heads_up_channel"

        // Create the notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with your app's icon
            .setContentTitle(title) // Title of the notification
            .setContentText(message) // Body of the notification
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Ensures heads-up behavior
            .setAutoCancel(true) // Dismiss notification when clicked
            .setVibrate(longArrayOf(0, 500, 200, 500)) // Vibration pattern
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Default sound, vibration, and lights
            .setStyle(NotificationCompat.BigTextStyle().bigText(message)) // Optional: Expandable notification
            .build()

        // Show the notification
        if (checkNotificationPermission()) {
            with(NotificationManagerCompat.from(context)) {
                notify(1001, notification) // Unique ID for this notification
            }
        }
    }

    private fun checkNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}
