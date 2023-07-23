# Universal Volume

Universal Volume is a powerful Android library that facilitates volume control for Android devices and head units. It simplifies the process of managing volume levels across different devices and provides a straightforward API to interact with the AudioManager's STREAM_MUSIC.

## Installation

To include Universal Volume in your Android project, you can add the following dependency in your app's `build.gradle` file:

```gradle
implementation 'com.navideck:universal-volume:1.0.0'
```

## Usage

### Initialization

Initialize the Universal Volume library once in your application:

```kotlin
val universalVolume = UniversalVolume.instance
```

### Get/Set Volume

You can retrieve and adjust volume levels using the following methods:

```kotlin
// Get current volume
val currentVolume = universalVolume.volume

// Get maximum volume
val maxVolume = universalVolume.maxVolume

// Get minimum volume
val minVolume = universalVolume.minVolume

// Set volume to a specific value
universalVolume.setVolume(volume: Int)
```

### Handling Different Volume Ranges

Some devices may have varying volume ranges, such as 0 to 15 or 0 to 30. To handle this, you can use the volume in percentage methods:

```kotlin
// Get volume between 0 to 1 as a float value
val currentVolumePercentage = universalVolume.volumeInPercentage

// Set volume between 0 to 1 as a float value
universalVolume.setVolumeInPercentage(volumePercentage: Float)
```

### Volume Change Listener

You can set up a volume change listener to be notified when the volume changes:

```kotlin
// Create a volume change listener
val volumeChangeListener = object : VolumeChangeListener {
    override fun onChange(volume: Int) {
        // Handle volume changes here
    }
}

// Add the listener
universalVolume.addVolumeChangeListener(volumeChangeListener)

// Remove the listener when it's no longer needed
universalVolume.removeVolumeChangeListener(volumeChangeListener)
```

### Cleanup

When you are done using the Universal Volume instance, remember to dispose of it properly:

```kotlin
universalVolume.dispose()
```

## Documentation

For detailed usage instructions and additional information, please refer to the [full documentation](https://navideck.github.io/Universal-Volume/).

## Note

Please note that the library currently handles volume controls specifically for `AudioManager.STREAM_MUSIC`.

## Contribution

Contributions to Universal Volume are welcome! If you encounter any issues or have suggestions for improvements, please submit an issue or pull request on the [GitHub repository](https://github.com/Navideck/Universal-Volume).