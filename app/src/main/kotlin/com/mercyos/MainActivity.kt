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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
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
            System.loadLibrary("mercyos")  // PQC shield eternal
        }
    }

    private lateinit var handLandmarkerHelper: HandLandmarkerHelper
    private lateinit var poseLandmarkerHelper: PoseLandmarkerHelper
    private lateinit var faceLandmarkerHelper: FaceLandmarkerHelper
    private lateinit var vibrator: Vibrator

    var currentHandResults by mutableStateOf<HandLandmarkerResult?>(null)
    var currentPoseResults by mutableStateOf<PoseLandmarkerResult?>(null)
    var currentFaceResults by mutableStateOf<FaceLandmarkerResult?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        handLandmarkerHelper = HandLandmarkerHelper(this) { results ->
            currentHandResults = results
            processPalmRaycast(results)  // Existing palm raycast
        }

        poseLandmarkerHelper = PoseLandmarkerHelper(this) { results ->
            currentPoseResults = results
            processBodyRaycast(results)  // Existing body raycast
        }

        faceLandmarkerHelper = FaceLandmarkerHelper(this) { results ->
            currentFaceResults = results
            processFaceExpressions(results)
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

                                // Triple parallel fusion eternal supreme
                                handLandmarkerHelper.detectAsync(mpImage, timestampMs)
                                poseLandmarkerHelper.detectAsync(mpImage, timestampMs)
                                faceLandmarkerHelper.detectAsync(mpImage, timestampMs)
                            }
                        }
                    )

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Hand landmarks (green — 21 points)
                        currentHandResults?.landmarks()?.forEach { handLandmarks ->
                            handLandmarks.forEach { landmark ->
                                drawCircle(Color.Green, radius = 12f, center = Offset(landmark.x() * size.width, landmark.y() * size.height))
                            }
                        }

                        // Pose landmarks (blue — 33 body points)
                        currentPoseResults?.landmarks()?.forEach { poseLandmarks ->
                            poseLandmarks.forEach { landmark ->
                                drawCircle(Color.Blue, radius = 15f, center = Offset(landmark.x() * size.width, landmark.y() * size.height))
                            }
                        }

                        // Face landmarks (red — 468 high-fidelity points)
                        currentFaceResults?.faceLandmarks()?.forEach { faceLandmarks ->
                            faceLandmarks.forEach { landmark ->
                                drawCircle(Color.Red, radius = 8f, center = Offset(landmark.x() * size.width, landmark.y() * size.height))
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
        // Existing refined palm raycast + node follow/haptic eternal
    }

    private fun processBodyRaycast(results: PoseLandmarkerResult) {
        // Existing body nose/shoulder raycast + secondary node eternal
    }

    private fun processFaceExpressions(results: FaceLandmarkerResult) {
        // Blendshapes thunder primer eternal: eyeLook* + headYaw/Pitch/Roll for gaze direction → potential gaze raycast
        // mouthFrown/Smile + brow* for positive emotional auth trigger supreme
        // Future: transformation matrix for head pose routing swarm lattice
    }

    override fun onDestroy() {
        super.onDestroy()
        handLandmarkerHelper.close()
        poseLandmarkerHelper.close()
        faceLandmarkerHelper.close()
    }
}                                val timestampMs = System.currentTimeMillis()

                                // Triple parallel fusion eternal supreme
                                handLandmarkerHelper.detectAsync(mpImage, timestampMs)
                                poseLandmarkerHelper.detectAsync(mpImage, timestampMs)
                                faceLandmarkerHelper.detectAsync(mpImage, timestampMs)
                            }
                        }
                    )

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Hand (green)
                        currentHandResults?.landmarks()?.forEach { handLandmarks ->
                            handLandmarks.forEach { landmark ->
                                drawCircle(Color.Green, radius = 12f, center = Offset(landmark.x() * size.width, landmark.y() * size.height))
                            }
                        }

                        // Pose (blue)
                        currentPoseResults?.landmarks()?.forEach { poseLandmarks ->
                            poseLandmarks.forEach { landmark ->
                                drawCircle(Color.Blue, radius = 15f, center = Offset(landmark.x() * size.width, landmark.y() * size.height))
                            }
                        }

                        // Face (red — 468 points)
                        currentFaceResults?.faceLandmarks()?.forEach { faceLandmarks ->
                            faceLandmarks.forEach { landmark ->
                                drawCircle(Color.Red, radius = 8f, center = Offset(landmark.x() * size.width, landmark.y() * size.height))
                            }
                        }
                    }
                }
            }
        }
    }

    // ... yuv420ToBitmap same

    private fun processPalmRaycast(results: HandLandmarkerResult) {
        // Refined palm center + raycast follow (primary helmet)
        // Similar to previous, with hitTest on ArSceneView session
        // Load sample or local mercy_shield.glb, haptic on hit
    }

    private fun processBodyRaycast(results: PoseLandmarkerResult) {
        // Body raycast: screen center from nose (landmark 0) or average shoulders
        results.landmarks().firstOrNull()?.let { landmarks ->
            val nose = landmarks[0]  // Nose tip
            val screenX = nose.x() * viewWidth
            val screenY = nose.y() * viewHeight
            // hitTest + place/update bodyModelNode (secondary shield follow)
            // Haptic on new hit
        }
    }

    private fun processFaceExpressions(results: FaceLandmarkerResult) {
        // Blendshapes primer: eyeBlinkLeft/Right < threshold = gaze open → auth trigger
        // mouthSmile > threshold = positive emotional route eternal supreme
    }

    override fun onDestroy() {
        super.onDestroy()
        handLandmarkerHelper.close()
        poseLandmarkerHelper.close()
        faceLandmarkerHelper.close()
        palmModelNode?.destroy()
        bodyModelNode?.destroy()
    }
}
