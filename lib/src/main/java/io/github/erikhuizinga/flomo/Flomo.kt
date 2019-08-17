package io.github.erikhuizinga.flomo

import android.content.Context
import io.github.erikhuizinga.flomo.internal.FlomoNetwork
import io.github.erikhuizinga.flomo.internal.flomoNetworkFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

/**
 * Flow network connectivity status ([Boolean]) in a non-blocking way.
 */
@ExperimentalCoroutinesApi
val Context.isNetworkConnectedFlow
    get() = flow {
        val connectedNetworks = mutableSetOf<FlomoNetwork>()
        flomoNetworkFlow.collect { flomoNetwork ->
            if (flomoNetwork.isConnected) {
                connectedNetworks += flomoNetwork
            } else {
                connectedNetworks -= flomoNetwork
            }
            emit(connectedNetworks.any())
        }
    }.distinctUntilChanged()
