package io.github.erikhuizinga.flomo.internal

import android.net.Network
import android.net.NetworkInfo

internal sealed class FlomoNetwork(val isConnected: Boolean) {
	abstract override fun equals(other: Any?): Boolean
	abstract override fun hashCode(): Int
}

internal class Flomo28Network(
	private val network: Network?,
	isConnected: Boolean
) : FlomoNetwork(isConnected) {
	override fun equals(other: Any?) = other is Flomo28Network && other.network == network
	override fun hashCode() = network?.hashCode() ?: 0
}

internal class FlomoCompatNetwork(
	private val networkInfo: NetworkInfo
) : FlomoNetwork(networkInfo.isConnected) {
	override fun equals(other: Any?) =
		other is FlomoCompatNetwork &&
				other.networkInfo.type == networkInfo.type &&
				other.networkInfo.subtype == networkInfo.subtype

	override fun hashCode() = 31 * networkInfo.type.hashCode() + networkInfo.subtype.hashCode()
}