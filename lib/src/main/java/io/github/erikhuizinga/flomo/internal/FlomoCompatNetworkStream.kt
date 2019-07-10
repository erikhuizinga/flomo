package io.github.erikhuizinga.flomo.internal

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
internal class FlomoCompatNetworkStream(override val producerScope: ProducerScope<FlomoNetwork>) :
	BroadcastReceiver(), FlomoNetworkStream {
	override fun subscribe(context: Context) {
		context.registerReceiver(
			this,
			IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
		)
	}

	override fun onReceive(context: Context?, intent: Intent?) {
		intent
			?.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
			?.let { networkInfo ->
				producerScope.apply { launch { send(FlomoCompatNetwork(networkInfo)) } }
			}
	}

	override fun unsubscribe(context: Context) =
		context.unregisterReceiver(this)
}
