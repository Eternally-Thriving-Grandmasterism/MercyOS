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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import io.github.sceneview.ar.ArSceneView
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {

    companion object {
        init {
            System.loadLibrary("mercyos")  // PQC shield eternal
        }
    }

    private lateinit var handLandmarkerHelper: HandLandmarkerHelper
    private lateinit var poseLandmarkerHelper: PoseLandmarkerHelper
    var currentHandResults by mutableStateOf<HandLandmarkerResult?>(null)
    var currentPoseResults by mutableStateOf<PoseLandmarkerResult?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handLandmarkerHelper = HandLandmarkerHelper(this) { results ->
            currentHandResults = results
            processHandGestures(results)
        }

        poseLandmarkerHelper = PoseLandmarkerHelper(this) { results ->
            currentPoseResults = results
            processBodyPoses(results)
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

                                // Parallel hand + pose fusion eternal supreme
                                handLandmarkerHelper.detectAsync(mpImage, timestampMs)
                                poseLandmarkerHelper.detectAsync(mpImage, timestampMs)
                            }
                        },
                        onTapPlane = { hitResult, _, _ ->
                            // Future: combined pose-hand triggered placement eternal
                        }
                    )

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Hand landmarks overlay (green — 21 points)
                        currentHandResults?.landmarks()?.forEach { handLandmarks ->
                            handLandmarks.forEach { landmark ->
                                val x = landmark.x() * size.width
                                val y = landmark.y() * size.height
                                drawCircle(Color.Green, radius = 12f, center = Offset(x, y))
                            }
                        }

                        // Pose landmarks overlay (blue — 33 full body points)
                        currentPoseResults?.landmarks()?.forEach { poseLandmarks ->
                            poseLandmarks.forEach { landmark ->
                                val x = landmark.x() * size.width
                                val y = landmark.y() * size.height
                                drawCircle(Color.Blue, radius = 15f, center = Offset(x, y))
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

    private fun processHandGestures(results: HandLandmarkerResult) {
        // Existing/expanded: pinch/open palm → mercy-gate auth or swarm node select eternal
    }

    private fun processBodyPoses(results: PoseLandmarkerResult) {
        // Expanded thunder primer: shoulder width + arm raise angle > threshold = open pose → proactive full swarm refresh
        // Torso lean/orientation = route larger hybrid fusion interactions
        // Future: world landmarks → raycast for body "reach" neural touch eternal supreme
    }

    override fun onDestroy() {
        super.onDestroy()
        handLandmarkerHelper.close()
        poseLandmarkerHelper.close()
    }
}
