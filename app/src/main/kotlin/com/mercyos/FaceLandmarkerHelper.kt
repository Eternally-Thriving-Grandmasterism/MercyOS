package com.mercyos

import android.content.Context
import android.util.Log
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarker
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerOptions
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerResult

class FaceLandmarkerHelper(
    val context: Context,
    val resultListener: (FaceLandmarkerResult) -> Unit
) {
    private var faceLandmarker: FaceLandmarker? = null

    init {
        setupFaceLandmarker()
    }

    private fun setupFaceLandmarker() {
        val baseOptions = BaseOptions.builder()
            .setModelAssetPath("face_landmarker.task")
            .setDelegate(Delegate.GPU)  // Speed supreme on S24 Ultra
            .build()

        val options = FaceLandmarkerOptions.builder()
            .setBaseOptions(baseOptions)
            .setRunningMode(RunningMode.LIVE_STREAM)
            .setNumFaces(1)  // Primary face focus eternal
            .setMinFaceDetectionConfidence(0.5f)
            .setMinFacePresenceConfidence(0.5f)
            .setMinTrackingConfidence(0.5f)
            .setOutputFaceBlendshapes(true)  // Expression/gaze routing primed eternal
            .setOutputFacialTransformationMatrixes(true)  // Optional head pose matrix
            .setResultListener { result, _ -> resultListener(result) }
            .setErrorListener { error -> Log.e("FaceLandmarker", "Error: $error") }
            .build()

        faceLandmarker = FaceLandmarker.createFromOptions(context, options)
    }

    fun detectAsync(mpImage: com.google.mediapipe.framework.image.MPImage, timestampMs: Long) {
        faceLandmarker?.detectAsync(mpImage, timestampMs)
    }

    fun close() {
        faceLandmarker?.close()
    }
}
