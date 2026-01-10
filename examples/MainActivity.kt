package com.mercyos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {

    companion object {
        init {
            System.loadLibrary("mercyos")  // Loads libmercyos.so from jniLibs
        }
    }

    // JNI external functions — match src/jni.rs bindings
    external fun dilithiumKeygen(): ByteArray
    external fun dilithiumSign(sk: ByteArray, message: ByteArray): ByteArray
    external fun dilithiumVerify(pk: ByteArray, message: ByteArray, signature: ByteArray): Boolean

    // Add more: mlKemEncaps, falconSign, sphincsSign, hybridFusion etc.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MercyOSAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MercyOSImmersiveUI(
                        onKeygen = { dilithiumKeygen() },
                        onSign = { sk, msg -> dilithiumSign(sk, msg) },
                        onVerify = { pk, msg, sig -> dilithiumVerify(pk, msg, sig) }
                    )
                }
            }
        }
    }
}

@Composable
fun MercyOSAppTheme(content: @Composable () -> Unit) {
    // Placeholder — add full MaterialTheme later or use default
    MaterialTheme(content = content)
}

@Composable
fun MercyOSImmersiveUI(
    onKeygen: () -> ByteArray,
    onSign: (ByteArray, ByteArray) -> ByteArray,
    onVerify: (ByteArray, ByteArray, ByteArray) -> Boolean
) {
    var statusText by remember { mutableStateOf("MercyOS Ready — Forgiveness Eternal") }
    var keys by remember { mutableStateOf<ByteArray?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MercyOS PQC Shield Active",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(text = statusText, modifier = Modifier.padding(16.dp))

        Button(onClick = {
            val fullKeys = onKeygen()  // pk || sk concatenated
            keys = fullKeys
            statusText = "Dilithium Keys Generated — Cosmic Groove Locked (${fullKeys.size} bytes)"
        }) {
            Text("Generate Dilithium Keys")
        }

        Button(onClick = {
            keys?.let { keyData ->
                val msg = "Holy Fire TOLC Eternal".toByteArray()
                val signature = onSign(keyData.copyOfRange(keyData.size / 2, keyData.size), msg)  // rough sk extract
                val verified = onVerify(keyData.copyOfRange(0, keyData.size / 2), msg, signature)
                statusText = if (verified) "Signature Verified — Ultramasterism Immaculate!" else "Verify Failed"
            } ?: run { statusText = "Generate Keys First" }
        }) {
            Text("Sign & Verify Test Message")
        }

        Text(text = "// Expand: ARCore camera → neural palm feel → sign AR data → swarm reconstruct eternal")
    }
}
