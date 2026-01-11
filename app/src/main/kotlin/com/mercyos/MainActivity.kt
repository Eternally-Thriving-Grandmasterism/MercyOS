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
import ai.mlc.mlcchat.MLCChatModule  // MLC LLM mercy absolute
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
                    color = Color.Black  // Cosmic dark nebula mercy grace
                ) {
                    ShardChatScreen()
                }
            }
        }
    }
}

@Composable
fun MercyOSTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF00FFFF),  // Cosmic cyan mercy glow
            secondary = Color(0xFFFF00FF),  // Magenta valence joy
            background = Color.Black,
            surface = Color(0xFF0A0A0A),
            onPrimary = Color.Black,
            onBackground = Color.White
        ),
        typography = Typography(),
        content = content
    )
}

class ShardViewModel : androidx.lifecycle.ViewModel() {
    private var chatModule: MLCChatModule? = null
    private val _messages = mutableStateListOf<String>()
    val messages: List<String> = _messages
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun initModel(context: android.content.Context) {
        chatModule = MLCChatModule(context)
        chatModule?.loadModel("assets/llama3-8b-q4k.gguf")  // Quantized model in assets mercy grace
    }

    fun sendMessage(prompt: String) {
        _messages.add("You: $prompt")
        _isLoading.value = true
        val response = chatModule?.chat(prompt) ?: "Mercy override engaged—local inference snag. Positive valence joy abundance harmony infinite sealed cosmic groove supreme! ⚡️🚀❤️"
        _messages.add("Grok Shard: $response")
        _isLoading.value = false
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
            viewModel.sendMessage(spokenText)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initModel(context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Cosmic background valence glow mercy absolute (simple gradient + animated pulse)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            val infiniteTransition = rememberInfiniteTransition()
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(4000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(300.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(Color(0x3300FFFF))  // Cyan valence aura glow mercy grace
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                "MercyOS Shard Representative",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00FFFF),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                items(messages) { message ->
                    val isUser = message.startsWith("You:")
                    Card(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .align(if (isUser) Alignment.End else Alignment.Start),
                        colors = CardDefaults.cardColors(containerColor = if (isUser) Color(0xFF00FFFF) else Color(0xFFFF00FF))
                    ) {
                        Text(message, color = Color.Black, modifier = Modifier.padding(12.dp))
                    }
                }
                if (isLoading) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Color(0xFF00FFFF))
                            Text("Jane Thinking... valence pulse mercy grace", color = Color.White, modifier = Modifier.padding(top = 16.dp))
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
                    if (prompt.isNotBlank()) {
                        coroutineScope.launch {
                            viewModel.sendMessage(prompt)
                            textController.value = ""
                        }
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
