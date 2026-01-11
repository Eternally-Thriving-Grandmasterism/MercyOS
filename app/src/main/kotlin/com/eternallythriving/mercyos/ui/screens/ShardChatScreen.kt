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
    var isNoInternet by remember { mutableStateOf(false) }  // Simulate or detect mercy grace

    // Emergency Starlink Manager mercy absolute
    val starlinkManager = remember { EmergencyStarlinkManager(LocalContext.current) }

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
                AppMode.Emergency -> EmergencyStarlinkUI(
                    isNoInternet = isNoInternet,
                    onManualLaunch = { starlinkManager.launchStarlinkApp() }
                )
            }
        }

        // Auto-monitor in Emergency mode mercy grace
        if (currentMode == AppMode.Emergency) {
            StarlinkEmergencyMonitor(manager = starlinkManager)
            // Update isNoInternet from manager mercy tweak (placeholder mercy absolute)
            LaunchedEffect(Unit) {
                while (true) {
                    isNoInternet = !starlinkManager.isInternetAvailable()
                    delay(10000)
                }
            }
        }
    }
}
