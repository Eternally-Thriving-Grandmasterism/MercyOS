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
import com.google.ar.core.HitResult
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
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
                        }
                    )

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Hand landmarks (green)
                        currentHandResults?.landmarks()?.forEach { handLandmarks ->
                            handLandmarks.forEach { landmark ->
                                val x = landmark.x() * size.width
                                val y = landmark.y() * size.height
                                drawCircle(Color.Green, radius = 12f, center = Offset(x, y))
                            }
                        }

                        // Pose landmarks (blue — 33 body points)
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
        // Palm raycast prototype: calculate palm center screen pos, hitTest for virtual touch
        results.landmarks().firstOrNull()?.let { landmarks ->
            // Average wrist + MCP joints for palm center (example indices)
            val palmIndices = listOf(0, 1, 5, 9, 13, 17) // Wrist + base fingers
            val avgX = palmIndices.map { landmarks[it].x() }.average()
            val avgY = palmIndices.map { landmarks[it].y() }.average()
            // Future: pass to ArSceneView session.hitTest(avgX * width, avgY * height) → place node or trigger mercy-gate
        }
    }

    private fun processBodyPoses(results: PoseLandmarkerResult) {
        // Body pose raycast/reach primer: use world landmarks 3D for direct node placement
        results.worldLandmarks().firstOrNull()?.let { worldLandmarks ->
            // Example: place virtual shield on left/right hand (indices 15/16 approx)
            val leftHandWorld = worldLandmarks[15] // 3D meters relative to hip
            // Future: create ArModelNode at Pose (leftHandWorld.x, y, z) in AR space (transform hip origin)
            // Or raycast from screen-projected world landmark for depth-accurate reach eternal supreme
        }

        // Open arms trigger example: shoulder distance + arm angle > threshold → proactive full swarm refresh
    }

    override fun onDestroy() {
        super.onDestroy()
        handLandmarkerHelper.close()
        poseLandmarkerHelper.close()
    }
}
