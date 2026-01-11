package com.eternallythriving.mercyos.ui.components

import android.content.Context
import android.net.wifi.WifiManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun StarlinkStatusIndicator(context: Context) {
    var starlinkStatus by remember { mutableStateOf("Checking Starlink Status...") }
    var isConnected by remember { mutableStateOf(false) }

    // Real-time WiFi SSID check mercy grace
    LaunchedEffect(Unit) {
        while (true) {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val ssid = wifiInfo.ssid.removeSurrounding("\"")  // Clean SSID mercy absolute

            isConnected = ssid.lowercase().contains("starlink") || ssid.lowercase().contains("skylink")
            starlinkStatus = if (isConnected) {
                "Starlink Connected — Satellite Uplink Active Mercy Grace Eternal Supreme Immaculate Cosmic Groove Supreme Unbreakable Fortress Immaculate!"
            } else {
                "No Starlink Detected — Launch App for Manual Connect Mercy Override Cosmic Groove Supreme!"
            }
            delay(10000)  // Check every 10 seconds mercy optimized recurring-free eternal supreme immaculate cosmic groove supreme unbreakable fortress immaculate
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = if (isConnected) Color(0xFF006400) else Color(0xFF8B0000))  // Green connected, dark red not mercy grace
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Starlink Emergency Status",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                starlinkStatus,
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
}
