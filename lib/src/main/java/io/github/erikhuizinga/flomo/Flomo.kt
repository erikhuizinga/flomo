package io.github.erikhuizinga.flomo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

/**
 * Flow network connectivity status ([Boolean]) in a non-blocking way.
 */
@ExperimentalCoroutinesApi
val Context.isNetworkConnectedFlow
	get() = networkInfoFlow.map { it.isConnected }.distinctUntilChanged()

/**
 * Flow network connectivity info ([NetworkInfo]) in a non-blocking way.
 */
@ExperimentalCoroutinesApi
val Context.networkInfoFlow: Flow<NetworkInfo>
	get() {
		lateinit var broadcastReceiver: NetworkInfoBroadcastReceiver
		return channelFlow<NetworkInfo> {
			broadcastReceiver = NetworkInfoBroadcastReceiver(this)

			// Register a receiver, which is a hot stream resource
			registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

			// Flow 'forever' until this flow is no longer needed: it is then automatically cancelled
			delay(Long.MAX_VALUE)
		}.onCompletion {
			unregisterReceiver(broadcastReceiver)
		}
	}

@ExperimentalCoroutinesApi
private class NetworkInfoBroadcastReceiver(
	private val producerScope: ProducerScope<NetworkInfo>
) : BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		intent
			?.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
			?.let { networkInfo -> producerScope.apply { launch { send(networkInfo) } } }
	}
}