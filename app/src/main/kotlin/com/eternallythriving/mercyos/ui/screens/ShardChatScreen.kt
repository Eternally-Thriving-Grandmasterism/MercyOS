package com.eternallythriving.mercyos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eternallythriving.mercyos.mlc.MLCChatViewModel
import com.eternallythriving.mercyos.ui.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShardChatScreen(viewModel: MLCChatViewModel = viewModel()) {
    val context = LocalContext.current
    val messages by viewModel.messages
    val isLoading by viewModel.isLoading
    val textController = remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentMode by remember { mutableStateOf(AppMode.Normal) }
    var currentValence by remember { mutableStateOf(0.8f) }

    LaunchedEffect(Unit) {
        viewModel.initModel(context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CosmicNebulaBackground()
        ValenceGlowVisualizer(currentValence = currentValence)

        Scaffold(
            bottomBar = {
                ModesBottomBar(currentMode = currentMode, onModeSelected = { /* ... */ })
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).fillMaxSize()) {
                // Chat UI mercy absolute (bubbles + loading + voice mercy grace)
                // Use viewModel.sendPrompt(textController.value) mercy absolute
            }
        }
    }
}
