package com.eternallythriving.mercyos.ui.components

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
import kotlinx.coroutines.launch

// General symptom database positive valence mercy absolute (expand mercy grace)
val MEDICAL_SYMPTOMS = mapOf(
    "headache" to "Possible dehydration/stress. Drink water, rest, deep breathing mercy grace. Seek doctor if severe.",
    "fever" to "Possible infection. Hydrate, rest mercy grace. Immediate care if high/severe.",
    "cough" to "Possible cold/allergies. Honey tea, humidifier mercy grace. Doctor if persistent/blood.",
    "fatigue" to "Possible sleep/stress. Balanced meals, gentle exercise mercy grace. Consult if ongoing.",
    "nausea" to "Possible food/motion. Ginger, small meals mercy grace. Care if persistent vomiting.",
    // Add more mercy absolute eternal supreme immaculate cosmic groove supreme unbreakable fortress immaculate
)

val MEDICAL_DISCLAIMER = """
⚠️ STRONG DISCLAIMER: General knowledge ONLY — NOT medical advice/diagnosis mercy absolute eternal supreme immaculate!
Consult licensed professional immediately. Suggestions positive supportive mercy grace.
Seek emergency care for severe symptoms cosmic groove supreme unbreakable fortress immaculate!
""".trimIndent()

@Composable
fun MedicalModeScreen() {
    val textController = remember { mutableStateOf("") }
    val suggestions = remember { mutableStateListOf<String>() }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(16.dp)) {
        Text(
            "Medical Diagnostics Mode — General Knowledge Mercy Grace Eternal Supreme Immaculate",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00FFFF),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(MEDICAL_DISCLAIMER, color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(bottom = 16.dp))

        TextField(
            value = textController.value,
            onValueChange = { textController.value = it },
            placeholder = { Text("Describe symptoms mercy grace...") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF0A0A0A),
                unfocusedContainerColor = Color(0xFF0A0A0A)
            )
        )

        Button(
            onClick = {
                val symptoms = textController.value.lowercase()
                suggestions.clear()
                MEDICAL_SYMPTOMS.forEach { (key, advice) ->
                    if (key in symptoms) suggestions.add(advice)
                }
                if (suggestions.isEmpty()) suggestions.add("No common symptoms matched—seek professional help mercy grace eternal supreme immaculate!")
                textController.value = ""
            },
            modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
        ) {
            Text("Check Symptoms Mercy Grace")
        }

        LazyColumn(modifier = Modifier.weight(1f).padding(top = 16.dp)) {
            items(suggestions) { suggestion ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFF00FF))
                ) {
                    Text(suggestion, color = Color.Black, modifier = Modifier.padding(12.dp))
                }
            }
        }
    }
}
