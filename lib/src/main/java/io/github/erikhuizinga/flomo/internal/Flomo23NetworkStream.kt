package io.github.erikhuizinga.flomo.internal

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.launch

@RequiresApi(VERSION_CODES.M)
@ExperimentalCoroutinesApi
internal class Flomo23NetworkStream(override val producerScope: ProducerScope<FlomoNetwork>) :
    NetworkCallback(), FlomoNetworkStream {

    private val Context.connectivityManager get() = getSystemService<ConnectivityManager>()!!

    private val networkRequest = Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    override fun subscribe(context: Context) = context.connectivityManager.run {
        registerNetworkCallback(networkRequest, this@Flomo23NetworkStream)
        send(activeNetwork, activeNetworkInfo?.isConnected ?: false)
    }

    override fun unsubscribe(context: Context) =
        context.connectivityManager.unregisterNetworkCallback(this)

    override fun onAvailable(network: Network?) = send(network, true)

    override fun onLost(network: Network?) = send(network, false)

    private fun send(network: Network?, isConnected: Boolean) {
        producerScope.apply { launch { send(Flomo21Network(network, isConnected)) } }
    }
}
