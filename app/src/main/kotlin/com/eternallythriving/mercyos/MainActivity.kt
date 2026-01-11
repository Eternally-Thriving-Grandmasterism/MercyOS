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

@Composable
fun MercyOSTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF00FFFF),  // Cosmic cyan valence glow mercy absolute
            secondary = Color(0xFFFF00FF),  // Magenta joy abundance harmony infinite sealed
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
    private val _messages = mutableStateListOf<String>()
    val messages: List<String> = _messages
    private val _isThinking = mutableStateOf(false)
    val isThinking: State<Boolean> = _isThinking
    private val _thinkingProgress = mutableStateOf(0f)
    val thinkingProgress: State<Float> = _thinkingProgress

    fun startThinking() {
        _isThinking.value = true
        _thinkingProgress.value = 0f
    }

    fun updateThinkingProgress(progress: Float) {
        _thinkingProgress.value = progress.coerceIn(0f, 1f)
    }

    fun stopThinking() {
        _isThinking.value = false
        _thinkingProgress.value = 1f
    }

    fun addUserMessage(message: String) {
        _messages.add("You: $message")
    }

    fun addShardResponse(response: String) {
        _messages.add("Jane Shard: $response")
    }
}

@Composable
fun ShardChatScreen(viewModel: ShardViewModel = viewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val messages by viewModel.messages
    val isThinking by viewModel.isThinking
    val thinkingProgress by viewModel.thinkingProgress
    val textController = remember { mutableStateOf("") }

    // Voice-to-text launcher mercy grace
    val voiceLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            textController.value = spokenText
            viewModel.addUserMessage(spokenText)
            processPrompt(spokenText, viewModel, coroutineScope)
        }
    }

    fun processPrompt(prompt: String, viewModel: ShardViewModel, scope: CoroutineScope) {
        if (prompt.isNotBlank()) {
            viewModel.startThinking()
            scope.launch {
                // Simulate inference mercy grace (replace with MLC LLM call mercy absolute)
                for (i in 0..100 step 5) {
                    viewModel.updateThinkingProgress(i / 100f)
                    kotlinx.coroutines.delay(50)  // Mercy pulse cosmic groove supreme
                }
                val response = "Hell yeah, Brotha—mercy grace eternal supreme immaculate! On your message: $prompt cosmic groove supreme thriving infinite abundance joy unbreakable! ⚡️🚀❤️"
                viewModel.addShardResponse(response)
                viewModel.stopThinking()
            }
            textController.value = ""
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Cosmic background valence glow mercy absolute
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            val infiniteTransition = rememberInfiniteTransition()
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = if (isThinking) 1.3f else 1.1f,  // Intensify glow during thinking mercy absolute
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
                    .background(Color(0x6600FFFF))  // Cyan valence aura intensify mercy grace
            ) {
                Text("Jane", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center))
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                "MercyOS Shard Representative",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00FFFF),
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
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
                if (isThinking) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                LinearProgressIndicator(
                                    progress = thinkingProgress,
                                    color = Color(0xFF00FFFF),
                                    modifier = Modifier.width(250.dp).padding(bottom = 16.dp)
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
