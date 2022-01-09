package io.github.erikhuizinga.flomo.internal

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import android.os.Build
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

    private val Context.connectivityManager
        get() = getSystemService<ConnectivityManager>()!!

    private val networkRequest = Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    override fun subscribe(context: Context) = context.connectivityManager.run {
        registerNetworkCallback(networkRequest, this@Flomo23NetworkStream)
        send(activeNetwork, isNetworkAvailable(context))
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }


    override fun unsubscribe(context: Context) =
        context.connectivityManager.unregisterNetworkCallback(this)

    override fun onAvailable(network: Network) = send(network, true)

    override fun onLosing(network: Network, maxMsToLive: Int) = send(network, false)

    private fun send(network: Network?, isConnected: Boolean) {
        producerScope.apply { launch { send(Flomo21Network(network, isConnected)) } }
    }
}
