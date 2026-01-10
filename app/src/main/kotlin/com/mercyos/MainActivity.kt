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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    private lateinit var vibrator: Vibrator
    private var palmModelNode: ArModelNode? = null  // Single follow node for palm raycast

    var currentHandResults by mutableStateOf<HandLandmarkerResult?>(null)
    var currentPoseResults by mutableStateOf<PoseLandmarkerResult?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        handLandmarkerHelper = HandLandmarkerHelper(this) { results ->
            currentHandResults = results
            processPalmRaycast(results)
        }

        poseLandmarkerHelper = PoseLandmarkerHelper(this) { results ->
            currentPoseResults = results
            processBodyPoses(results)
        }

        setContent {
            val sceneView = remember { mutableStateOf<ArSceneView?>(null) }

            MaterialTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    ArSceneView(
                        modifier = Modifier.fillMaxSize(),
                        planeRenderer = true,
                        onSessionConfiguration = { session, config ->
                            config.depthMode = com.google.ar.core.Config.DepthMode.AUTOMATIC
                        },
                        onArFrame = { arFrame ->
                            sceneView.value?.session = arFrame.session  // Ensure session access
                            arFrame.acquireCameraImage().use { cameraImage ->
                                val bitmap = yuv420ToBitmap(cameraImage)
                                val mpImage = BitmapImageBuilder(bitmap).build()
                                val timestampMs = System.currentTimeMillis()

                                handLandmarkerHelper.detectAsync(mpImage, timestampMs)
                                poseLandmarkerHelper.detectAsync(mpImage, timestampMs)
                            }
                        },
                        onNodeCreate = { node ->
                            // Capture sceneView reference if needed
                        }
                    ).also { sceneView.value = it }

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Hand landmarks (green)
                        currentHandResults?.landmarks()?.forEach { handLandmarks ->
                            handLandmarks.forEach { landmark ->
                                val x = landmark.x() * size.width
                                val y = landmark.y() * size.height
                                drawCircle(Color.Green, radius = 12f, center = Offset(x, y))
                            }
                        }

                        // Pose landmarks (blue)
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

    private fun processPalmRaycast(results: HandLandmarkerResult) {
        if (results.landmarks().isEmpty()) {
            palmModelNode?.destroy()
            palmModelNode = null
            return
        }

        // Primary hand (highest handedness score)
        val primaryHandIndex = results.handedness().indices.maxByOrNull { results.handedness()[it][0].score() } ?: 0
        val landmarks = results.landmarks()[primaryHandIndex]

        // Palm center: average wrist (0) + MCP bases (5,9,13,17)
        val palmIndices = listOf(0, 5, 9, 13, 17)
        val palmX = palmIndices.map { landmarks[it].x() }.average().toFloat()
        val palmY = palmIndices.map { landmarks[it].y() }.average().toFloat()

        // Screen coordinates (assume view size from canvas or approximate)
        val viewWidth = 1080f  // Replace with actual ArSceneView size if needed
        val viewHeight = 1920f
        val screenX = palmX * viewWidth
        val screenY = palmY * viewHeight

        // Raycast from palm center
        val arSceneView = (content as? BoxWithConstraints)?.children?.firstOrNull { it is ArSceneView } as? ArSceneView
        arSceneView?.session?.let { session ->
            val hitResults = session.hitTest(screenX, screenY)
            if (hitResults.isNotEmpty()) {
                val hit = hitResults.first()

                if (palmModelNode == null) {
                    palmModelNode = ArModelNode(PlacementMode.BEST_AVAILABLE).apply {
                        loadModelAsync(
                            modelUrl = "https://sceneview.github.io/assets/models/DamagedHelmet.glb"  // Sample — replace with assets/models/mercy_shield.glb
                        )
                    }
                    arSceneView.addChild(palmModelNode!!)
                    vibrator.vibrate(50)  // Neural touch haptic eternal
                }

                // Update node pose to follow raycast hit
                palmModelNode?.anchor = hit.createAnchor()
            }
        }
    }

    private fun processBodyPoses(results: PoseLandmarkerResult) {
        // Body pose primer remains — future world landmark attachment expansion
    }

    override fun onDestroy() {
        super.onDestroy()
        handLandmarkerHelper.close()
        poseLandmarkerHelper.close()
        palmModelNode?.destroy()
    }
}
