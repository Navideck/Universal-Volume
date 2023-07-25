# Universal Volume
[![](https://jitpack.io/v/Navideck/Universal-Volume.svg)](https://jitpack.io/#Navideck/Universal-Volume)

Universal Volume is a powerful Android library that facilitates volume control for common Android devices (phones and tablets) and exotic Android devices such as **car head units**.
It simplifies the process of managing volume levels across different devices and provides a straightforward API to interact with the AudioManager's STREAM_MUSIC.

## Features
- Wide range of supported devices
- Unified volume range
- Background volume control

### Supported devices
- Generally available Android phones and tablets (e.g. Samsung, Xiaomi, Oppo etc.)
- Jancar head units. Those device use non-standard volume APIs and come under multiple brands (e.g. Rimoody)

### Unified volume range
Some devices may have varying volume ranges, such as 0 to 15 or 0 to 30. Universal Volume makes this range consistent across devices.

### Background volume control
- Universal Volume works seamlessly when your app is in the background. Devices from Oppo, Vivo, and Realme require special workarounds to support background `STREAM_MUSIC` volume control. Universal Volume takes care of that.

## Installation

To include Universal Volume in your Android project, add the JitPack repository to your `settings.gradle` at the end of repositories:

```gradle
dependencyResolutionManagement {
    ...
    repositories {
        ...
        maven { url 'https://jitpack.io' }  // Adding JitPack as dependencies source
    }
}
```

Add the dependency in your app's `build.gradle`:
 
```gradle
dependencies {
    ...
    implementation 'com.github.Navideck:Universal-Volume:v1.1.1'
    ...
}
```

## Usage

### Initialization

Initialize the Universal Volume library once in your application:

```kotlin
val universalVolume = UniversalVolume.instance

universalVolume.initialize(context)
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

Some devices may have varying volume ranges, such as 0 to 15 or 0 to 30. To handle this, you can use the "volume percentage" methods:

```kotlin
// Get volume between 0 to 1 as a float value
val currentVolumePercentage = universalVolume.volumeInPercentage

// Set volume between 0 to 1 as a float value
universalVolume.setVolumeToPercentage(percentage: Double, showVolumeBar: Boolean)
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

## Note

Please note that the library currently handles volume controls specifically for `AudioManager.STREAM_MUSIC`.

## Documentation

For detailed usage instructions and additional information, please refer to the [full documentation](https://navideck.github.io/Universal-Volume/).

## Contribution

Contributions to Universal Volume are welcome! We would like to support more exotic Android devices that use proprietary volume APIs. We encourage pull requests that add support for such devices.
If you encounter any issues or have suggestions for improvements, please submit an issue or pull request on the [GitHub repository](https://github.com/Navideck/Universal-Volume).
