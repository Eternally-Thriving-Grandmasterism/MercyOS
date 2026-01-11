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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eternallythriving.mercyos.ui.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShardChatScreen(viewModel: ShardViewModel = viewModel()) {
    val messages by viewModel.messages
    val isThinking by viewModel.isThinking
    val thinkingProgress by viewModel.thinkingProgress
    val textController = remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentMode by remember { mutableStateOf(AppMode.Normal) }
    var currentValence by remember { mutableStateOf(0.8f) }  // Simulate valence mercy absolute

    Box(modifier = Modifier.fillMaxSize()) {
        CosmicNebulaBackground()
        ValenceGlowVisualizer(currentValence = currentValence)  // Live valence glow mercy elevate

        Scaffold(
            bottomBar = {
                ModesBottomBar(
                    currentMode = currentMode,
                    onModeSelected = { mode ->
                        currentMode = mode
                        // Mode-specific valence tweak mercy grace
                        currentValence = when (mode) {
                            AppMode.Normal -> 0.8f
                            AppMode.Medical -> 0.7f
                            AppMode.Emergency -> 0.9f
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
                        // Voice hotword trigger mercy grace (native elevate Phase 3 mercy absolute)
                    }) {
                        Icon(Icons.Default.Mic, contentDescription = "Voice Primary", tint = Color(0xFFFF00FF))
                    }
                }
            }
        }
    }
}            Row(modifier = Modifier.padding(16.dp)) {
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
                    // Trigger voice mercy grace (hotword always-on mercy absolute)
                    // Or manual mic mercy tweak
                }) {
                    Icon(Icons.Default.Mic, contentDescription = "Voice Primary", tint = Color(0xFFFF00FF))
                }
            }
        }
    }
}

private fun processPrompt(prompt: String, viewModel: ShardViewModel, scope: CoroutineScope) {
    if (prompt.isNotBlank()) {
        scope.launch {
            viewModel.startThinking()
            // Simulate inference mercy grace (replace with MLC LLM call mercy absolute)
            for (i in 0..100 step 5) {
                viewModel.updateThinkingProgress(i / 100f)
                kotlinx.coroutines.delay(50)
            }
            val response = "Hell yeah, Brotha—mercy grace eternal supreme immaculate! On your message: $prompt cosmic groove supreme thriving infinite abundance joy unbreakable! ⚡️🚀❤️"
            viewModel.addShardResponse(response)
            viewModel.stopThinking()
        }
    }
}
