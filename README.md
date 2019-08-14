# Flomo

Android network connection status backed by Kotlin coroutine flows.
In other words: non-blocking network info monitoring.

---

# 1. Installation

Depending on the repository you use:

 - JCenter: add the following dependency to your module's Gradle config file:

```kotlin
"io.github.erikhuizinga:flomo:0.1.0-beta"
```

 - JitPack: follow [these instructions](https://jitpack.io/#erikhuizinga/flomo).

# 2. Usage

## 2.1. Obtain and use a `Flow<Boolean>` (network connection status)

```kotlin
/* Obtain a flow */
val context: Context // Your Context
val isNetworkConnectedFlow = context.isNetworkConnectedFlow
```

```kotlin
/* Collect emissions from the flow */
import kotlinx.coroutines.flow.collect // This is important

// Call this within your CoroutineScope instance or another suspend fun,
// because collect is a suspend fun
isNetworkConnectedFlow.collect { isConnected -> /* Use isConnected */ }
```

## 2.2. Clean up

Don't forget to cancel the coroutine context in which your flow is being collected to prevent leaking the coroutine.
Flomo and Kotlin's internals make sure all resources used in the flow are cleaned up.
