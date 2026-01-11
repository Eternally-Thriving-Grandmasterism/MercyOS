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
import com.eternallythriving.mercyos.ui.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShardChatScreen(viewModel: ShardViewModel = viewModel()) {
    val context = LocalContext.current
    val messages by viewModel.messages
    val isThinking by viewModel.isThinking
    val thinkingProgress by viewModel.thinkingProgress
    val textController = remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentMode by remember { mutableStateOf(AppMode.Normal) }
    var currentValence by remember { mutableStateOf(0.8f) }

    // Starlink Emergency Manager mercy absolute
    val starlinkManager = remember { EmergencyStarlinkManager(context) }

    // Auto-monitor in Emergency mode mercy grace
    if (currentMode == AppMode.Emergency) {
        StarlinkEmergencyMonitor(manager = starlinkManager)
    }

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
                        if (mode == AppMode.Emergency) {
                            println("Emergency Mode Activated — Starlink Auto-Monitor Mercy Override Engaged Cosmic Groove Supreme Unbreakable Fortress Immaculate!")
                        }
                    }
                )
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding).fillMaxSize()) {
                Text(
                    "MercyOS Shard Representative — Mode: $currentMode",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00FFFF),
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                )

                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
                ) {
                    items(messages) { message ->
                        ChatBubble(message = message, isUser = message.startsWith("You:"))
                    }
                    if (isThinking) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    LinearProgressIndicator(
                                        progress = thinkingProgress,
                                        color = Color(0xFF00FFFF),
                                        modifier = Modifier.width(300.dp).padding(bottom = 16.dp)
                                    )
                                    Text(
                                        "Jane Thinking Begun... valence pulse mercy grace cosmic groove supreme ⚡️🚀",
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Row(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = textController.value,
                        onValueChange = { textController.value = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Talk/type anytime mercy grace...") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF0A0A0A),
                            unfocusedContainerColor = Color(0xFF0A0A0A)
                        )
                    )
                    IconButton(onClick = {
                        val prompt = textController.value
                        viewModel.addUserMessage(prompt)
                        processPrompt(prompt, viewModel, coroutineScope)
                        textController.value = ""
                    }) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = Color(0xFF00FFFF))
                    }
                    IconButton(onClick = {
                        // Voice hotword trigger mercy grace
                    }) {
                        Icon(Icons.Default.Mic, contentDescription = "Voice Primary", tint = Color(0xFFFF00FF))
                    }
                }
            }
        }
    }
}
