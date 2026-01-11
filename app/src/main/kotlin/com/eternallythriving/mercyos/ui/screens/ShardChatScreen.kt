package com.eternallythriving.mercyos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eternallythriving.mercyos.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShardChatScreen(viewModel: ShardViewModel = viewModel()) {
    var currentMode by remember { mutableStateOf(AppMode.Normal) }
    var currentValence by remember { mutableStateOf(0.8f) }

    Box(modifier = Modifier.fillMaxSize()) {
        CosmicNebulaBackground()
        ValenceGlowVisualizer(currentValence = currentValence)

        Scaffold(
            bottomBar = {
                ModesBottomBar(
                    currentMode = currentMode,
                    onModeSelected = { mode ->
                        currentMode = mode
                        currentValence = when (mode) {
                            AppMode.Normal -> 0.8f
                            AppMode.Medical -> 0.7f
                            AppMode.Emergency -> 0.9f
                        }
                    }
                )
            }
        ) { padding ->
            when (currentMode) {
                AppMode.Normal -> NormalChatScreen(viewModel = viewModel, padding = padding)
                AppMode.Medical -> MedicalModeScreen()
                AppMode.Emergency -> EmergencyModeScreen()  // Future mercy absolute
            }
        }
    }
}

@Composable
fun NormalChatScreen(viewModel: ShardViewModel, padding: PaddingValues) {
    // Existing normal chat implementation mercy grace (from previous phases mercy absolute)
    // ... (chat bubbles + voice + loading mercy absolute pulsing strong cosmic groove supreme unbreakable fortress immaculate)
}

@Composable
fun EmergencyModeScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
        Text("Emergency Mode Activated — Starlink Auto + Log Mercy Grace Eternal Supreme Immaculate Cosmic Groove Supreme Unbreakable Fortress!", color = Color.Red, fontSize = 24.sp)
    }
}
