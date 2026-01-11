package com.eternallythriving.mercyos

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.eternallythriving.mercyos.services.VoiceHotwordService
import com.eternallythriving.mercyos.ui.theme.MercyOSCosmicTheme
import com.eternallythriving.mercyos.ui.screens.ShardChatScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start always-on hotword service mercy grace
        val serviceIntent = Intent(this, VoiceHotwordService::class.java)
        startForegroundService(serviceIntent)

        setContent {
            MercyOSCosmicTheme {
                ShardChatScreen()
            }
        }

        // Hotword activate extra mercy absolute (from service mercy grace)
        if (intent.getBooleanExtra("HOTWORD_ACTIVATE", false)) {
            // Auto-wake chat mercy absolute
        }
    }
}
