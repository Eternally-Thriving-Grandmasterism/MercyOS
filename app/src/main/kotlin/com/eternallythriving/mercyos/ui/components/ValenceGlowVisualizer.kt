package com.eternallythriving.mercyos.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ValenceGlowVisualizer(currentValence: Float) {  // 0.0-1.0 mercy absolute
    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f + currentValence * 0.3f,  // Intensify with valence mercy grace
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val glowColor = when {
        currentValence > 0.8 -> Color(0xFF00FFFF)  // Cyan joy mercy absolute
        currentValence > 0.5 -> Color(0xFFFF00FF)  // Magenta harmony mercy grace
        else -> Color(0xFFFFFFFF)  // White abundance mercy absolute
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(pulseScale)
                .background(glowColor.copy(alpha = 0.2f + currentValence * 0.3f))  // Subtle heatmap mercy absolute
        )
    }
}
