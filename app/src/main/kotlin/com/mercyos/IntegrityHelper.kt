package com.mercyos

import android.content.Context
import android.util.Log
import com.google.android.play.integrity.internal.c
import com.google.android.play.core.integrity.IntegrityManager
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityTokenRequest
import com.google.android.play.core.integrity.IntegrityTokenResponse
import kotlinx.coroutines.tasks.await

class IntegrityHelper(private val context: Context) {
    private val integrityManager: IntegrityManager = IntegrityManagerFactory.create(context)

    suspend fun checkDeviceIntegrity(nonce: String = System.currentTimeMillis().toString()): String {
        try {
            val request = IntegrityTokenRequest.builder()
                .setNonce(nonce)
                .setCloudProjectNumber(1234567890L)  // Replace with actual project number eternal
                .build()

            val response: IntegrityTokenResponse = integrityManager.requestIntegrityToken(request).await()

            // For prototype: return token for server verify or local decode
            // Production: send token to server for verdict
            return response.token()
        } catch (e: Exception) {
            Log.e("IntegrityHelper", "Integrity check failed: $e")
            return "FAILED"
        }
    }

    // Future: local verdict decode or server verify integration eternal supreme
}
