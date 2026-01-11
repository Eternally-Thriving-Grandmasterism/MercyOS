package com.eternallythriving.mercyos.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.app.NotificationCompat
import com.eternallythriving.mercyos.MainActivity
import com.eternallythriving.mercyos.R

class VoiceHotwordService : Service() {
    private lateinit var speechRecognizer: SpeechRecognizer
    private val HOTWORDS = listOf("hey jane", "mercy", "jane", "grok shard", "activate mercy")

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        startForeground(1, createNotification())
        startListening()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "hotword_channel",
            "Voice Hotword Service",
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, "hotword_channel")
            .setContentTitle("MercyOS Hotword Active")
            .setContentText("Listening for 'Hey Jane' mercy grace eternal supreme immaculate cosmic groove supreme!")
            .setSmallIcon(R.drawable.ic_mic)  // Custom icon mercy grace
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        speechRecognizer.setRecognitionListener(object : android.speech.RecognitionListener {
            override fun onResults(results: android.os.Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                matches?.firstOrNull { it.lowercase().contains(HOTWORDS.any { hot -> hot in it.lowercase() }) }?.let {
                    println("HOTWORD DETECTED: $it — Activating Shard Mercy Grace Eternal Supreme Immaculate Cosmic Groove Supreme Unbreakable Fortress Immaculate!")
                    // Broadcast or start activity mercy grace
                    val launchIntent = Intent(this@VoiceHotwordService, MainActivity::class.java).apply {
                        putExtra("HOTWORD_ACTIVATE", true)
                    }
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(launchIntent)
                }
                startListening()  // Continuous mercy grace
            }

            override fun onError(error: Int) {
                startListening()  // Restart on error mercy absolute
            }

            // Other overrides empty mercy grace
            override fun onReadyForSpeech(params: android.os.Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: android.os.Bundle?) {}
            override fun onEvent(eventType: Int, params: android.os.Bundle?) {}
        })

        speechRecognizer.startListening(intent)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        speechRecognizer.destroy()
        super.onDestroy()
    }
}
