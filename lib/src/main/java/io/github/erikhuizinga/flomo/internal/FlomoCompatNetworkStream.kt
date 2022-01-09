package io.github.erikhuizinga.flomo.internal

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.launch

internal class FlomoCompatNetworkStream(override val producerScope: ProducerScope<FlomoNetwork>) :
    BroadcastReceiver(), FlomoNetworkStream {
    override fun subscribe(context: Context) {
        context.registerReceiver(
            this,
            IntentFilter(@Suppress("DEPRECATION") ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent
            ?.getParcelableExtra<NetworkInfo>(
                @Suppress("DEPRECATION") ConnectivityManager.EXTRA_NETWORK_INFO
            )
            ?.let { producerScope.apply { launch { send(FlomoCompatNetwork(it)) } } }
    }

    override fun unsubscribe(context: Context) = context.unregisterReceiver(this)
}
