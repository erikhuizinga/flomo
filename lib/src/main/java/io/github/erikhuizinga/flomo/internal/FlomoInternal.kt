package io.github.erikhuizinga.flomo.internal

import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.onCompletion

@ExperimentalCoroutinesApi
internal val Context.flomoNetworkFlow: Flow<FlomoNetwork>
    get() {
        lateinit var flomoNetworkStream: FlomoNetworkStream

        return channelFlow {
            flomoNetworkStream = when {
                VERSION.SDK_INT >= VERSION_CODES.M -> Flomo23NetworkStream(this)
                else -> FlomoCompatNetworkStream(this)
            }

            flomoNetworkStream.subscribe(this@flomoNetworkFlow)

            awaitClose()
        }.onCompletion {
            flomoNetworkStream.unsubscribe(this@flomoNetworkFlow)
        }
    }
