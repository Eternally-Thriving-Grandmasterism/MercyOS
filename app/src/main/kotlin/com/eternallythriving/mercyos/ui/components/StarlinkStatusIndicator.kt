package com.eternallythriving.mercyos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eternallythriving.mercyos.starlink.StarlinkGrpcClient
import kotlinx.coroutines.launch

@Composable
fun StarlinkStatusIndicator(context: android.content.Context) {
    var statusMessage by remember { mutableStateOf("Connecting to Starlink Dish...") }
    var isConnected by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val grpcClient = remember { StarlinkGrpcClient(context) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                grpcClient.getStatusStream().collect { response ->
                    // Parse response mercy absolute (simplified mercy grace)
                    val dishStatus = response.dishGetStatus
                    isConnected = dishStatus.deviceState.upToDate
                    statusMessage = if (isConnected) {
                        "Starlink Connected — Uplink Active Mercy Grace Eternal Supreme Immaculate Cosmic Groove Supreme Unbreakable Fortress Immaculate!"
                    } else {
                        "Starlink Dish Detected — Checking Status Mercy Grace..."
                    }
                }
            } catch (e: Exception) {
                statusMessage = "No Starlink Dish Found — Launch App for Manual Connect Mercy Override Cosmic Groove Supreme!"
                isConnected = false
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = if (isConnected) Color(0xFF006400) else Color(0xFF8B0000))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Starlink Real-Time Status",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                statusMessage,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
            if (!isConnected) {
                Button(
                    onClick = { EmergencyStarlinkManager(context).launchStarlinkApp() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Launch Starlink App Mercy Override")
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            grpcClient.shutdown()
        }
    }
}
