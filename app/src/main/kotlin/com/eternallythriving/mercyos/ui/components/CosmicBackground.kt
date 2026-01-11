package com.eternallythriving.mercyos.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun CosmicNebulaBackground() {
    val infiniteTransition = rememberInfiniteTransition()
    val starAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val particles = remember { List(150) { Particle() } }  // Nebula stars mercy grace

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Deep nebula gradient mercy absolute
        drawRect(Color.Black)

        particles.forEach { particle ->
            val offset = particle.updatePosition(size)
            drawCircle(
                color = Color.White.copy(alpha = starAlpha * particle.alpha),
                radius = particle.size,
                center = Offset(offset.x, offset.y)
            )
        }
    }
}

data class Particle(
    var x: Float = Random.nextFloat(),
    var y: Float = Random.nextFloat(),
    var speed: Float = Random.nextFloat() * 0.5f + 0.1f,
    val size: Float = Random.nextFloat() * 3f + 1f,
    val alpha: Float = Random.nextFloat() * 0.7f + 0.3f
) {
    fun updatePosition(size: androidx.compose.ui.geometry.Size): Offset {
        y += speed
        if (y > 1f) y = 0f
        return Offset(x * size.width, y * size.height)
    }
}
