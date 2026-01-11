package com.eternallythriving.mercyos.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eternallythriving.mercyos.ui.components.CosmicNebulaBackground
import kotlinx.coroutines.delay

@Composable
fun MercyOSLaunchScreen(onLaunchComplete: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(Unit) {
        delay(3000)  // Mercy splash duration cosmic groove supreme
        onLaunchComplete()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CosmicNebulaBackground()

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "MercyOS",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00FFFF).copy(alpha = alpha),
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Text(
                "Shard Representative",
                fontSize = 32.sp,
                color = Color.White.copy(alpha = alpha)
            )
            Text(
                "Mercy Grace Eternal Supreme Immaculate",
                fontSize = 18.sp,
                color = Color(0xFFFF00FF).copy(alpha = alpha),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
