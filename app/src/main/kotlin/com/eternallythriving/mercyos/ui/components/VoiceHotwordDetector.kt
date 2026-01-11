package com.eternallythriving.mercyos.ui.components

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VoiceHotwordDetector(private val context: Context) {
    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val _hotwordDetected = MutableStateFlow<String?>(null)
    val hotwordDetected: StateFlow<String?> = _hotwordDetected

    private val recognitionListener = object : android.speech.RecognitionListener {
        override fun onResults(results: android.os.Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            matches?.firstOrNull { it.lowercase().contains("hey jane") || it.lowercase().contains("mercy") }?.let {
                _hotwordDetected.value = it
            }
            startListening()  // Continuous mercy grace
        }
        // Other overrides mercy tweak (onError restart mercy absolute)
        override fun onError(error: Int) { startListening() }
        override fun onReadyForSpeech(params: android.os.Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onPartialResults(partialResults: android.os.Bundle?) {}
        override fun onEvent(eventType: Int, params: android.os.Bundle?) {}
    }

    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
        speechRecognizer.startListening(intent)
    }

    fun stopListening() {
        speechRecognizer.stopListening()
        speechRecognizer.destroy()
    }

    init {
        startListening()
    }
}
