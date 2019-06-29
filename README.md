# Flomo

Android network connection status backed by Kotlin coroutine flows.
In other words: non-blocking network info monitoring.

---

# 1. Installation

<details>

<summary>🚧 WIP</summary>

Add the following dependencies to you submodule's Gradle config file:

```
"io.github.erikhuizinga:flomo:0.0.0-coroutines-1.3.0-M2"
"org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.0-M2"
```

</details>

# 2. Usage

## 2.1. Obtain a `Flow`

### 2.1.a. Network info: `Flow<NetworkInfo>`

```kotlin
val context: Context // Your Context
val networkInfoFlow = context.networkInfoFlow
```

### 2.1.b. Network connectivity status: `Flow<Boolean>`

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

There is no need to close resources or cancel the flow, because that is handled by Flomo and Kotlin's flows.
Don't forget to cancel the coroutine context in which your flow is being collected to prevent leaking the coroutine.
