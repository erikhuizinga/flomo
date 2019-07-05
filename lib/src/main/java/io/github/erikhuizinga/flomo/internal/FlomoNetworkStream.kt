package io.github.erikhuizinga.flomo.internal

import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope

@ExperimentalCoroutinesApi
internal interface FlomoNetworkStream<in FN : FlomoNetwork> {
	val producerScope: ProducerScope<FN>
	fun subscribe(context: Context)
	fun unsubscribe(context: Context)
}
