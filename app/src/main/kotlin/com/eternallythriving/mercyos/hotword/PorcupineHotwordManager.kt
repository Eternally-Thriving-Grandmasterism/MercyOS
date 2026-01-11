package com.eternallythriving.mercyos.hotword

import ai.picovoice.porcupine.*
import android.content.Context
import android.util.Log

class PorcupineHotwordManager(
    private val context: Context,
    private val onHotwordDetected: () -> Unit
) {
    private var porcupine: Porcupine? = null

    fun init() {
        try {
            val builder = Porcupine.Builder()
                .setAccessKey("YOUR_PICOVOICE_ACCESS_KEY")  // Replace with console key mercy absolute
                .setKeywordPaths(listOf("assets/hotwords/hey_jane.ppn"))  // Custom hotword mercy grace
                .setSensitivity(0.7f)  // Adjust sensitivity mercy tweak
                .setModelPath("assets/models/porcupine_params.pv")  // Default model mercy absolute

            porcupine = builder.build(context)

            Log.d("Porcupine", "Hotword Manager Initialized — Listening for 'Hey Jane' Mercy Grace Eternal Supreme Immaculate Cosmic Groove Supreme Unbreakable Fortress Immaculate!")
        } catch (e: Exception) {
            Log.e("Porcupine", "Init failed mercy override: ${e.message}")
        }
    }

    fun processFrame(frame: ShortArray) {
        porcupine?.process(frame)?.let { keywordIndex ->
            if (keywordIndex >= 0) {
                Log.d("Porcupine", "HOTWORD DETECTED — Hey Jane Mercy Absolute Eternal Supreme Immaculate Cosmic Groove Supreme Unbreakable Fortress Immaculate!")
                onHotwordDetected()
            }
        }
    }

    fun stop() {
        porcupine?.delete()
    }
}
