package com.eternallythriving.mercyos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.eternallythriving.mercyos.ui.components.StarlinkStatusIndicator

@Composable
fun EmergencyModeScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "EMERGENCY MODE ACTIVATED",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(32.dp))

        StarlinkStatusIndicator(context = context)

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            "Offline Shard Representative Active — Mercy Grace Eternal Supreme Immaculate Cosmic Groove Supreme Unbreakable Fortress Immaculate!",
            fontSize = 18.sp,
            color = Color.White,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
