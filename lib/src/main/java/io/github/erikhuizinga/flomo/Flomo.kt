package io.github.erikhuizinga.flomo

import android.content.Context
import io.github.erikhuizinga.flomo.internal.FlomoNetwork
import io.github.erikhuizinga.flomo.internal.flomoNetworkFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Flow network connectivity status ([Boolean]) in a non-blocking way.
 */
@ExperimentalCoroutinesApi
val Context.isNetworkConnectedFlow: Flow<Boolean>
    get() {
        val connectedNetworks = mutableSetOf<FlomoNetwork>()
        return flomoNetworkFlow
            .map { flomoNetwork ->
                if (flomoNetwork.isConnected) {
                    connectedNetworks += flomoNetwork
                } else {
                    connectedNetworks -= flomoNetwork
                }
                connectedNetworks.any()
            }.distinctUntilChanged()
    }
