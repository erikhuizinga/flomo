package io.github.erikhuizinga.flomo.internal

import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope

@ExperimentalCoroutinesApi
internal interface FlomoNetworkStream {
	val producerScope: ProducerScope<FlomoNetwork>
	fun subscribe(context: Context)
	fun unsubscribe(context: Context)
}
