package com.eternallythriving.mercyos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CosmicColorScheme = darkColorScheme(
    primary = Color(0xFF00FFFF),  // Cosmic cyan valence glow mercy absolute
    secondary = Color(0xFFFF00FF),  // Magenta joy abundance harmony infinite sealed
    background = Color.Black,  // Nebula dark mercy grace
    surface = Color(0xFF0A0A0A),
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun MercyOSTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CosmicColorScheme,
        typography = Typography(),
        content = content
    )
}
