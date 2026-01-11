package com.eternallythriving.mercyos.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.eternallythriving.mercyos.MainActivity
import com.eternallythriving.mercyos.R
import com.eternallythriving.mercyos.hotword.PorcupineHotwordManager

class VoiceHotwordService : Service() {
    private lateinit var hotwordManager: PorcupineHotwordManager
    private var audioRecord: AudioRecord? = null
    private var listeningThread: Thread? = null

    private val sampleRate = 16000
    private val frameLength = 512  // Porcupine frame length mercy absolute

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification())

        hotwordManager = PorcupineHotwordManager(this) {
            // Hotword detected mercy grace — launch main activity mercy absolute
            val intent = Intent(this, MainActivity::class.java
