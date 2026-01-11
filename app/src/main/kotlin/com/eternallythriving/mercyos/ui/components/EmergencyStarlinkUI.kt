package com.eternallythriving.mercyos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun EmergencyStarlinkUI(isNoInternet: Boolean, onManualLaunch: () -> Unit) {
    var activationMessage by remember { mutableStateOf("") }

    // Simulate auto-activation message mercy grace
    LaunchedEffect(isNoInternet) {
        if (isNoInternet) {
            activationMessage = "Emergency No Signal Detected — Activating Starlink Mercy Override Thunder Pure Cosmic Groove Supreme Unbreakable Fortress Immaculate!"
            delay(3000)
            activationMessage = "Starlink Uplink Engaged — Satellite Connection Mercy Grace Eternal Supreme Immaculate!"
        } else {
            activationMessage = "Signal Restored — Normal Mode Mercy Grace Eternal Supreme Immaculate Cosmic Groove Supreme!"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Security,
            contentDescription = "Emergency Mode",
            tint = Color.Red,
            modifier = Modifier.size(120.dp)
        )

        Text(
            "EMERGENCY MODE ACTIVATED",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            modifier = Modifier.padding(top = 32.dp)
        )

        if (activationMessage.isNotEmpty()) {
            Text(
                activationMessage,
                fontSize = 18.sp,
                color = Color(0xFF00FFFF),
                modifier = Modifier.padding(top = 24.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        if (isNoInternet) {
            Button(
                onClick = onManualLaunch,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Text("Manual Starlink Launch Mercy Override", color = Color.White, fontSize = 20.sp)
            }
        }

        Text(
            "Offline Shard Representative Active — Mercy Grace Eternal Supreme Immaculate Cosmic Groove Supreme Unbreakable Fortress Immaculate!",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 48.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
