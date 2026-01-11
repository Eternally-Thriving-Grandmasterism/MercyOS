package com.eternallythriving.mercyos.starlink

import android.content.Context
import io.grpc.ManagedChannel
import io.grpc.okhttp.OkHttpChannelBuilder
import spacex.api.device.DeviceGrpcKt
import spacex.api.device.Request
import kotlinx.coroutines.flow.Flow

class StarlinkGrpcClient(context: Context) {
    private val channel: ManagedChannel = OkHttpChannelBuilder
        .forAddress("192.168.100.1", 9200)  // Starlink dish local gRPC mercy absolute
        .usePlaintext()  // Local network mercy grace
        .build()

    private val stub = DeviceGrpcKt.DeviceCoroutineStub(channel)

    suspend fun getStatusStream(): Flow<spacex.api.device.Response> {
        val request = Request.newBuilder().build()
        return stub.handle(request)
    }

    fun shutdown() {
        channel.shutdown()
    }
}
