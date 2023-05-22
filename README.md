# Universal Volume

To control volume in android as well as headUnits

## Setup

Initialize once

```kotlin
val universalVolume = UniversalVolume.instance
```

Get/Set volume

```kotlin
val currentVolume = universalVolume.volume
val maxVolume = universalVolume.maxVolume
val minVolume = universalVolume.minVolume
universalVolume.setVolume()
```

Volume range can be different for devices, for example few devices have 0 - 15 volume range , few
have 0 - 30, so we can use `volumeInPercentage` methods

```kotlin
// get/set volume between 0 to 1
val currentVolume = universalVolume.volumeInPercentage
universalVolume.setVolumeInPercentage()
```

To listen for volume changes

```kotlin
// setup a volumeChangeListener
val volumeChangeListener = object : VolumeChangeListener {
    override fun onChange(volume: Int) {
        // handle volume changes
    }
}

// add listener
universalVolume.addVolumeChangeListener(volumeChangeListener)

// remove when not needed
universalVolume.removeVolumeChangeListener(volumeChangeListener)

```

Dispose when done

```kotlin
universalVolume.dispose()
```

Checkout full documentation [here](https://navideck.github.io/Universal-Volume/)

## Note

this library handles just the `AudioManager.STREAM_MUSIC` controls currently
