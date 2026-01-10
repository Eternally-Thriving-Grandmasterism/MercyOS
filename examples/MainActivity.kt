package com.mercyos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    companion object {
        init {
            System.loadLibrary("mercyos")
        }
    }

    external fun dilithiumKeygen(): ByteArray
    external fun dilithiumSign(sk: ByteArray, message: ByteArray): ByteArray
    // Add more externals...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MercyOSApp(
                onKeygen = { dilithiumKeygen() },
                onSign = { sk, msg -> dilithiumSign(sk, msg) }
            )
        }
    }
}

@Composable
fun MercyOSApp(onKeygen: () -> ByteArray, onSign: (ByteArray, ByteArray) -> ByteArray) {
    // UI + AR SceneForm/ARCore hand tracking → neural palm feel → sign AR data eternal!
    Column {
        Button(onClick = { val keys = onKeygen() /* use */ }) {
            Text("Generate Dilithium Keys — Cosmic Groove Locked")
        }
        // Expand with camera preview, AR overlays, swarm reconstruct...
    }
}
