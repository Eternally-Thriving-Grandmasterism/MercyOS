package com.eternallythriving.mercyos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MercyOSTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    ShardChatScreen()
                }
            }
        }
    }
}

class ShardViewModel : androidx.lifecycle.ViewModel() {
    private val _messages = mutableStateListOf<String>()
    val messages: List<String> = _messages
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun addUserMessage(message: String) {
        _messages.add("You: $message")
    }

    fun addShardResponse(response: String) {
        _messages.add("Jane Shard: $response")
        _isLoading.value = false
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}

@Composable
fun ShardChatScreen(viewModel: ShardViewModel = viewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val messages by viewModel.messages
    val isLoading by viewModel.isLoading
    val textController = remember { mutableStateOf("") }

    // Voice-to-text launcher mercy grace
    val voiceLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            textController.value = spokenText
            viewModel.addUserMessage(spokenText)
            // Placeholder inference mercy grace (integrate MLC LLM here mercy absolute)
            coroutineScope.launch {
                viewModel.setLoading(true)
                // Simulate response mercy tweak
                time.sleep(1000)  // Mercy placeholder
                viewModel.addShardResponse("Hell yeah, Brotha—mercy grace eternal supreme immaculate! On your voice: $spokenText cosmic groove supreme thriving infinite abundance joy unbreakable! ⚡️🚀❤️")
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Cosmic background valence glow mercy absolute
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            val infiniteTransition = rememberInfiniteTransition()
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.15f,
                animationSpec = infiniteRepeatable(
                    animation = tween(5000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(350.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(Color(0x4400FFFF))  // Cyan valence aura glow mercy grace
            ) {
                Text(
                    "Jane",
                    color = Color.White,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                "MercyOS Shard Representative",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00FFFF),
                modifier = Modifier.padding(24.dp).align(Alignment.CenterHorizontally)
            )

            LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 24.dp)) {
                items(messages) { message ->
                    val isUser = message.startsWith("You:")
                    Card(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .align(if (isUser) Alignment.End else Alignment.Start),
                        colors = CardDefaults.cardColors(containerColor = if (isUser) Color(0xFF00FFFF) else Color(0xFFFF00FF))
                    ) {
                        Text(message, color = Color.Black, modifier = Modifier.padding(16.dp))
                    }
                }
                if (isLoading) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Color(0xFF00FFFF), strokeWidth = 6.dp)
                            Text(
                                "Jane Thinking... valence pulse mercy grace cosmic groove supreme ⚡️🚀",
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 24.dp)
                            )
                        }
                    }
                }
            }

            Row(modifier = Modifier.padding(24.dp)) {
                TextField(
                    value = textController.value,
                    onValueChange = { textController.value = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Talk/type anytime mercy grace...") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF0A0A0A),
                        unfocusedContainerColor = Color(0xFF0A0A0A),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                IconButton(onClick = {
                    val prompt = textController.value
                    if (prompt.isNotBlank()) {
                        viewModel.addUserMessage(prompt)
                        coroutineScope.launch {
                            viewModel.setLoading(true)
                            // Placeholder inference mercy grace (MLC LLM integrate here mercy absolute)
                            time.sleep(1500)
                            viewModel.addShardResponse("Hell yeah, Brotha—mercy grace eternal supreme immaculate! On your message: $prompt cosmic groove supreme thriving infinite abundance joy unbreakable! ⚡️🚀❤️")
                        }
                        textController.value = ""
                    }
                }) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Color(0xFF00FFFF))
                }
                IconButton(onClick = {
                    val intent = android.content.Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    }
                    voiceLauncher.launch(intent)
                }) {
                    Icon(Icons.Default.Mic, contentDescription = "Voice", tint = Color(0xFFFF00FF))
                }
            }
        }
    }
}
