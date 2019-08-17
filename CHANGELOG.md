# Change log

## `v0.1.0-beta` (2019-08-17):

Flomo directly depends on `kotlinx-coroutines` for `Flow`, which currently is an `@ExperimentalCoroutinesApi`, so anything exposed by Flomo also is experimental.
Kotlin's flow API might change from version to version and with those changes Flomo's API might change as well.

 - Removed: `val Context.networkInfoFlow: Flow<NetworkInfo>` has been removed, but [an issue (#1)](https://github.com/erikhuizinga/flomo/issues/1) has been made to expose network information in a different way.
 - New: internal optimisations for performance and reliability.
Now, `Context.isNetworkConnectedFlow` more reliably emits `true` or `false` if any network gains or loses a connection respectively.
 - Change: this release exposes `kotlinx-coroutines` as an `api` dependency.
 - Change: this release depends on `kotlinx-coroutines` v1.3.0-RC2.
 - Internal: migrate Gradle scripts to the Gradle Kotlin DSL.

## `v0.0.0-coroutines-1.3.0-M2` (2019-06-30):

**🎉 Initial release!**

Note the version name containing the version of the Kotlin coroutines dependency: Kotlin flows are maturing, but are still in an experimental state. Because Flomo depends on a non-stable version of Kotlin coroutines, Flomo's interoperability with other Kotlin coroutines versions might not be good. It is recommended to use this specific Kotlin coroutines version as a dependency if you're using Flomo.

 - New: `val Context.networkInfoFlow: Flow<NetworkInfo>`
 - New: `val Context.isNetworkConnectedFlow: Flow<Boolean>`
 - New: A demo app is included in the project. It uses `isNetworkConnectedFlow` in a `ViewModel` to expose network connectivity status to an activity with a reactive UI.
