package io.github.erikhuizinga.flomo.internal

import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.onCompletion

@ExperimentalCoroutinesApi
internal val Context.flomoNetworkFlow: Flow<FlomoNetwork>
	get() {
		lateinit var flomoNetworkStream: FlomoNetworkStream<*>

		@Suppress("RemoveExplicitTypeArguments")
		return channelFlow<FlomoNetwork> {
			flomoNetworkStream = FlomoCompatNetworkStream(this)

			flomoNetworkStream.subscribe(this@flomoNetworkFlow)

			// Flow 'forever' until this flow is no longer needed: it is then automatically cancelled
			delay(Long.MAX_VALUE)
		}.onCompletion {
			flomoNetworkStream.unsubscribe(this@flomoNetworkFlow)
		}
	}
