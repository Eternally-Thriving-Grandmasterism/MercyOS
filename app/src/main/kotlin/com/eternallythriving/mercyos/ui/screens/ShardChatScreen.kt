package com.eternallythriving.mercyos.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.eternallythriving.mercyos.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShardChatScreen(viewModel: ShardViewModel = viewModel()) {
    var currentMode by remember { mutableStateOf(AppMode.Normal) }
    var currentValence by remember { mutableStateOf(0.8f) }

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
            AppMode.Emergency -> EmergencyModeScreen()
        }
    }
}
