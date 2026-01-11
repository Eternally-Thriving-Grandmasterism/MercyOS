package com.eternallythriving.mercyos.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.eternallythriving.mercyos.MainActivity
import com.eternallythriving.mercyos.R
import com.eternallythriving.mercyos.hotword.PorcupineHotwordManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder

class VoiceHotwordService : Service() {
    private lateinit var hotwordManager: PorcupineHotwordManager
    private var audioRecord: AudioRecord? = null
    private var listeningThread: Thread? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        hotwordManager = PorcupineHotwordManager(this
