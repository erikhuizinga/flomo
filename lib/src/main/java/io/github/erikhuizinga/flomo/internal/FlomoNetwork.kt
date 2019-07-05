package io.github.erikhuizinga.flomo.internal

internal sealed class FlomoNetwork(val isConnected: Boolean) {
	abstract override fun equals(other: Any?): Boolean
	abstract override fun hashCode(): Int
}
