package com.mercyos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.ar.rememberArSceneView

class MainActivity : ComponentActivity() {

    companion object {
        init {
            System.loadLibrary("mercyos")  // PQC shield active
        }
    }

    // JNI externals here (from previous)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val sceneView = rememberArSceneView()

                ArSceneView(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),  // Cosmic immersive backdrop
                    planeRenderer = true,
                    onSessionConfiguration = { session, config ->
                        config.depthMode = com.google.ar.core.Config.DepthMode.AUTOMATIC
                        config.lightEstimationMode = com.google.ar.core.Config.LightEstimationMode.AMBIENT_INTENSITY
                    },
                    onTapPlane = { hitResult, plane, motionEvent ->
                        val anchorNode = io.github.sceneview.ar.node.ArAnchorNode(hitResult.createAnchor())
                        sceneView.addChild(anchorNode)

                        val modelNode = ArModelNode(PlacementMode.BEST_AVAILABLE).apply {
                            loadModelAsync(
                                modelUrl = "https://sceneview.github.io/assets/models/DamagedHelmet.glb"  // Sample remote — replace with local assets/models/mercy_shield.glb
                            ) {
                                // Success: swarm node placed — future hand gesture trigger
                            }
                        }
                        anchorNode.addChild(modelNode)
                    }
                )
            }
        }
    }
}
