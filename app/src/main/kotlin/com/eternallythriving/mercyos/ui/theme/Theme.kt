package com.eternallythriving.mercyos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CosmicDarkColorScheme = darkColorScheme(
    primary = Color(0xFF00FFFF),     // Cosmic cyan valence glow mercy absolute
    secondary = Color(0xFFFF00FF),   // Magenta joy abundance harmony infinite sealed
    tertiary = Color(0xFF00FFAA),    // Teal cosmic accent mercy grace
    background = Color.Black,        // Deep space nebula mercy absolute
    surface = Color(0xFF0D0D0D),     // Dark surface mercy grace
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun MercyOSCosmicTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CosmicDarkColorScheme,
        typography = Typography(),
        content = content
    )
}
