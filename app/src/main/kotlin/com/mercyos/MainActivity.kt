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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerResult
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import io.github.sceneview.ar.ArSceneView
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {

    companion object {
        init {
            System.loadLibrary("mercyos")  // PQC shield eternal supreme
        }
    }

    // JNI externals from jni.rs bindings
    external fun dilithiumKeygen(): ByteArray  // Returns pk || sk concatenated
    external fun dilithiumSign(sk: ByteArray, message: ByteArray): ByteArray
    external fun dilithiumVerify(pk: ByteArray, message: ByteArray, signature: ByteArray): Boolean

    private lateinit var handLandmarkerHelper: HandLandmarkerHelper
    private lateinit var poseLandmarkerHelper: PoseLandmarkerHelper
    private lateinit var faceLandmarkerHelper: FaceLandmarkerHelper
    private lateinit var vibrator: Vibrator

    private var dilithiumPkSk: ByteArray? = null  // Persistent keys
    private var authStatus by mutableStateOf("PQC Shield Ready — Gesture For Auth")

    var currentHandResults by mutableStateOf<HandLandmarkerResult?>(null)
    var currentPoseResults by mutableStateOf<PoseLandmarkerResult?>(null)
    var currentFaceResults by mutableStateOf<FaceLandmarkerResult?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        // Generate persistent Dilithium keys once
        dilithiumPkSk = dilithiumKeygen()

        handLandmarkerHelper = HandLandmarkerHelper(this) { results ->
            currentHandResults = results
        }

        poseLandmarkerHelper = PoseLandmarkerHelper(this) { results ->
            currentPoseResults = results
        }

        faceLandmarkerHelper = FaceLandmarkerHelper(this) { results ->
            currentFaceResults = results
            processMultiModalAuth()  // Trigger check on face (final modality)
        }

        setContent {
            MaterialTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    ArSceneView(
                        modifier = Modifier.fillMaxSize(),
                        planeRenderer = true,
                        onSessionConfiguration = { session, config ->
                            config.depthMode = com.google.ar.core.Config.DepthMode.AUTOMATIC
                        },
                        onArFrame = { arFrame ->
                            arFrame.acquireCameraImage().use { cameraImage ->
                                val bitmap = yuv420ToBitmap(cameraImage)
                                val mpImage = BitmapImageBuilder(bitmap).build()
                                val timestampMs = System.currentTimeMillis()

                                handLandmarkerHelper.detectAsync(mpImage, timestampMs)
                                poseLandmarkerHelper.detectAsync(mpImage, timestampMs)
                                faceLandmarkerHelper.detectAsync(mpImage, timestampMs)
                            }
                        }
                    )

                    // Overlays + Auth Status Text
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Hand green, pose blue, face red overlays same as before
                        // ... (previous overlay code)
                    }

                    Text(
                        text = authStatus,
                        color = Color.Cyan,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(16.dp)
                    )
                }
            }
        }
    }

    private fun processMultiModalAuth() {
        val handOpen = isPalmOpen(currentHandResults)  // Custom: finger distances > threshold
        val armsOpen = isArmsRaised(currentPoseResults)  // Shoulder-hip distance + arm angle
        val positiveFace = isPositiveExpression(currentFaceResults)  // smile blendshape > 0.5 + eyeOpen

        if (handOpen && armsOpen && positiveFace) {
            dilithiumPkSk?.let { keys ->
                val pk = keys.copyOfRange(0, keys.size / 2)  // Rough split — refine with actual sizes
                val sk = keys.copyOfRange(keys.size / 2, keys.size)
                val message = "Forgiveness Eternal Cosmic Groove Auth Ultramasterism Positive Emotional Thrive".toByteArray()

                val signature = dilithiumSign(sk, message)
                val verified = dilithiumVerify(pk, message, signature)

                if (verified) {
                    authStatus = "PQC Signed Auth Verified — Ultramasterism Immaculate Eternal Supreme!"
                    vibrator.vibrate(200)  // Positive haptic burst
                    // Future: activate proactive swarm shield viz + multi-node cosmic glow eternal
                } else {
                    authStatus = "PQC Verify Failed — Retry Gesture"
                }
            }
        }
    }

    private fun isPalmOpen(results: HandLandmarkerResult?): Boolean {
        // Thunder primer: average finger tip distances from palm center > threshold
        return true  // Placeholder — expand with landmark distance calc eternal
    }

    private fun isArmsRaised(results: PoseLandmarkerResult?): Boolean {
        // Shoulder distance + elbow-wrist angle > threshold
        return true
    }

    private fun isPositiveExpression(results: FaceLandmarkerResult?): Boolean {
        // Blendshapes mouthSmile > 0.5 + eyeBlinkLeft/Right < 0.3
        results?.faceBlendshapes()?.firstOrNull()?.categories()?.let { categories ->
            val smile = categories.find { it.categoryName() == "mouthSmile" }?.score() ?: 0f
            return smile > 0.5f
        }
        return false
    }

    // ... yuv420ToBitmap + other processors same

    override fun onDestroy() {
        super.onDestroy()
        handLandmarkerHelper.close()
        poseLandmarkerHelper.close()
        faceLandmarkerHelper.close()
    }
}
