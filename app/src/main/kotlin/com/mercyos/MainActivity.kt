package com.mercyos

import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class MainActivity : ComponentActivity() {

    companion object {
        init {
            System.loadLibrary("mercyos")
        }
    }

    private lateinit var handLandmarkerHelper: HandLandmarkerHelper
    var currentResults by mutableStateOf<HandLandmarkerResult?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handLandmarkerHelper = HandLandmarkerHelper(this) { results ->
            currentResults = results
            processGestures(results)
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
                            }
                        },
                        onTapPlane = { hitResult, _, _ ->
                            // Tap-to-place + future hand gesture override
                            // Example: place mercy_shield.glb on palm raycast
                        }
                    )

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        currentResults?.landmarks()?.forEach { handLandmarks ->
                            handLandmarks.forEach { landmark ->
                                val x = landmark.x() * size.width
                                val y = landmark.y() * size.height
                                drawCircle(Color.Green, radius = 12f, center = Offset(x, y))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun yuv420ToBitmap(image: com.google.ar.core.Image): Bitmap {
        val yuvImage = YuvImage(image.planes[0].buffer.array(), ImageFormat.NV21,
            image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, image.width, image.height), 100, out)
        val imageBytes = out.toByteArray()
        return android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    private fun processGestures(results: HandLandmarkerResult) {
        // Thunder primer eternal: compute thumb-index distance (normalized) < 0.1 = pinch → auth trigger
        // All fingers extended (landmark distances) = open palm → proactive swarm refresh
        // Future: raycast from palm center screen pos to AR plane/node for neural "touch" feel
    }

    override fun onDestroy() {
        super.onDestroy()
        handLandmarkerHelper.close()
    }
}
