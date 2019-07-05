# Flomo

Android network connection status backed by Kotlin coroutine flows.
In other words: non-blocking network info monitoring.

---

# 1. Installation

Depending on the repository you use:

 - JCenter: add the following dependency to your module's Gradle config file:

```kotlin
"io.github.erikhuizinga:flomo:0.0.0-coroutines-1.3.0-M2"
```

 - JitPack: follow [these instructions](https://jitpack.io/#erikhuizinga/flomo).

# 2. Usage

## 2.1. Obtain a `Flow`

```kotlin
val context: Context // Your Context
val isNetworkConnectedFlow = context.isNetworkConnectedFlow
```

## 2.2. Collect emissions from the `Flow`

```kotlin
import kotlinx.coroutines.flow.collect // This is important

// 'flow' is your Flow instance
// Call this within your CoroutineScope instance, because collect is a suspend fun
flow.collect { emission -> /* Use emission */ }
```

## 2.3. Clean up

Don't forget to cancel the coroutine context in which your flow is being collected to prevent leaking the coroutine.
Flomo and Kotlin's internals make sure all resources used in the flow are cleaned up.
