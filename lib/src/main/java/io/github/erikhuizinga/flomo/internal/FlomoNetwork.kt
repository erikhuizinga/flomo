package io.github.erikhuizinga.flomo.internal

import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi

internal sealed class FlomoNetwork(val isConnected: Boolean) {
    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
}

internal class Flomo21Network(private val network: Network?, isConnected: Boolean) :
    FlomoNetwork(isConnected) {
    override fun equals(other: Any?) = other is Flomo21Network && other.network == network
    override fun hashCode() = network?.hashCode() ?: 0
}

internal class FlomoCompatNetwork(private val networkInfo: NetworkInfo) :
    FlomoNetwork(networkInfo.isConnected) {
    override fun equals(other: Any?) =
        other is FlomoCompatNetwork &&
            @Suppress("DEPRECATION") other.networkInfo.type ==
            @Suppress("DEPRECATION") networkInfo.type &&
            other.networkInfo.subtype == networkInfo.subtype

    override fun hashCode() =
        31 * @Suppress("DEPRECATION") networkInfo.type.hashCode() + networkInfo.subtype.hashCode()
}
