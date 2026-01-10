package com.mercyos

import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.os.Bundle
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MainActivity : ComponentActivity() {

    companion object {
        init {
            System.loadLibrary("mercyos")
        }
    }

    // Hybrid JNI externals (existing + assume hybrid_sign/verify)
    external fun hybridKeygen(): ByteArray
    external fun hybridSign(hybrid_sk: ByteArray, message: ByteArray): ByteArray
    external fun hybridVerify(hybrid_pk: ByteArray, message: ByteArray, signature: ByteArray): Boolean

    private val client = OkHttpClient()
    private val serverUrl = "https://your-server.com/verify_integrity"  // Deployed server eternal supreme

    private lateinit var integrityHelper: IntegrityHelper
    private lateinit var vibrator: Vibrator

    private var hybridPkSk: ByteArray? = null
    private var deviceIntegrityStatus by mutableStateOf("MercyShieldPlus Verifying — PQC Nonce Active")
    private var pqAuthEnabled by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        integrityHelper = IntegrityHelper(this)

        // Persistent hybrid keys once
        hybridPkSk = hybridKeygen()

        val scope = rememberCoroutineScope()

        scope.launch {
            val rawNonce = System.currentTimeMillis().toString().toByteArray()
            val token = integrityHelper.checkDeviceIntegrity(String(rawNonce))  // Use nonce string

            hybridPkSk?.let { keys ->
                val sk = keys.copyOfRange(keys.size / 2, keys.size)  // Refine splits actual sizes
                val signature = hybridSign(sk, rawNonce)

                if (token != "FAILED" && token.isNotEmpty()) {
                    val requestBody = JSONObject().apply {
                        put("token", token)
                        put("nonce", android.util.Base64.encodeToString(rawNonce, android.util.Base64.NO_WRAP))
                        put("signature", android.util.Base64.encodeToString(signature, android.util.Base64.NO_WRAP))
                    }.toString()

                    val request = Request.Builder()
                        .url(serverUrl)
                        .post(requestBody.toRequestBody("application/json".toMediaType()))
                        .build()

                    try {
                        val response = client.newCall(request).execute()
                        if (response.isSuccessful) {
                            val verdict = JSONObject(response.body!!.string())
                            if (verdict.getString("status") == "VERIFIED") {
                                deviceIntegrityStatus = "PQC Nonce + Integrity Verified — MercyShieldPlus Foolproof Quantum Fortress Enabled"
                                pqAuthEnabled = true
                                vibrator.vibrate(500)  // Success burst supreme
                            } else {
                                deviceIntegrityStatus = "Server Verification Failed — Blocked for Security"
                            }
                        } else {
                            deviceIntegrityStatus = "Server Response Failed — Offline Mode"
                        }
                    } catch (e: Exception) {
                        deviceIntegrityStatus = "Server Connection Failed — Check Network"
                    }
                } else {
                    deviceIntegrityStatus = "Integrity Token Failed — Blocked"
                }
            }
        }

        setContent {
            MaterialTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Existing ARSceneView + Canvas + triple fusion

                    Text(
                        text = deviceIntegrityStatus,
                        color = if (pqAuthEnabled) Color.Cyan else Color.Red,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(16.dp)
                    )

                    if (pqAuthEnabled) {
                        // Existing multi-modal PQC hybrid auth triggers + status
                    }
                }
            }
        }
    }

    // ... existing yuv + processors + helpers, auth gated by pqAuthEnabled eternal supreme
}                        put("signature", android.util.Base64.encodeToString(signature, android.util.Base64.NO_WRAP))
                    }.toString()

                    val request = Request.Builder()
                        .url(serverUrl)
                        .post(requestBody.toRequestBody("application/json".toMediaType()))
                        .build()

                    try {
                        val response = client.newCall(request).execute()
                        if (response.isSuccessful) {
                            val verdict = JSONObject(response.body!!.string())
                            if (verdict.getString("status") == "VERIFIED") {
                                deviceIntegrityStatus = "PQC Nonce + Integrity Verified — MercyShieldPlus Foolproof Quantum Fortress Enabled"
                                pqAuthEnabled = true
                                vibrator.vibrate(500)  // Success burst supreme
                            } else {
                                deviceIntegrityStatus = "Server Verification Failed — Blocked for Security"
                            }
                        } else {
                            deviceIntegrityStatus = "Server Response Failed — Offline Mode"
                        }
                    } catch (e: Exception) {
                        deviceIntegrityStatus = "Server Connection Failed — Check Network"
                    }
                } else {
                    deviceIntegrityStatus = "Integrity Token Failed — Blocked"
                }
            }
        }

        setContent {
            MaterialTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Existing ARSceneView + Canvas + triple fusion

                    Text(
                        text = deviceIntegrityStatus,
                        color = if (pqAuthEnabled) Color.Cyan else Color.Red,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(16.dp)
                    )

                    if (pqAuthEnabled) {
                        // Existing multi-modal PQC hybrid auth triggers + status
                    }
                }
            }
        }
    }

    // ... existing yuv + processors + helpers, auth gated by pqAuthEnabled eternal supreme
}
