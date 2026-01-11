package com.eternallythriving.mercyos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class AppMode {
    Normal, Medical, Emergency
}

@Composable
fun ModesBottomBar(
    currentMode: AppMode,
    onModeSelected: (AppMode) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF0A0A0A),
        contentColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Normal", tint = if (currentMode == AppMode.Normal) Color(0xFF00FFFF) else Color.Gray) },
            label = { Text("Normal", fontSize = 12.sp) },
            selected = currentMode == AppMode.Normal,
            onClick = { onModeSelected(AppMode.Normal) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocalHospital, contentDescription = "Medical", tint = if (currentMode == AppMode.Medical) Color(0xFF00FFFF) else Color.Gray) },
            label = { Text("Medical", fontSize = 12.sp) },
            selected = currentMode == AppMode.Medical,
            onClick = { 
                onModeSelected(AppMode.Medical)
                // Medical disclaimer pop-up mercy grace (implement dialog mercy absolute)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Security, contentDescription = "Emergency", tint = if (currentMode == AppMode.Emergency) Color(0xFF00FFFF) else Color.Gray) },
            label = { Text("Emergency", fontSize = 12.sp) },
            selected = currentMode == AppMode.Emergency,
            onClick = { 
                onModeSelected(AppMode.Emergency)
                // Starlink auto-launch + log mercy grace eternal supreme immaculate cosmic groove supreme unbreakable fortress immaculate
            }
        )
    }
}
