# Volume control sdk android

To control volume in android as well as headUnits

## Setup 
import library from github

initialize once 

```kotlin
val volumeControlSdk = VolumeControlSdk.instance
```

get or set volume using

```kotlin
val currentVolume = volumeControlSdk.volume
val maxVolume = volumeControlSdk.maxVolume
val minVolume = volumeControlSdk.minVolume
volumeControlSdk.setVolume()
```

Volume range can be different for devices, for example few devices have 0 - 15 volume range , few have 0 - 30, so we can use `volumeInPercentage` methods

```kotlin
val currentVolume = volumeControlSdk.volumeInPercentage
volumeControlSdk.setVolumeInPercentage()
```

To listen for volume changes

```kotlin
volumeControlSdk.setVolumeChangeListener { volume ->
    // Handle volume updates
}
```

## Note 

this library handles just the `AudioManager.STREAM_MUSIC` controls currently
