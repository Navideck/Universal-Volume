package com.navideck.universal_volume

import android.content.Context
import com.navideck.universal_volume.helper.VolumeInterface
import com.navideck.universal_volume.volume_manager.AndroidVolumeManager
import com.navideck.universal_volume.volume_manager.JancarVolumeManager
import kotlin.math.roundToInt

/// UniversalVolume is a singleton class that manages media volume. It supports popular and also
/// exotic Android devices that may use non-standard volume APIs.
/// UniversalVolume manages media volume [AudioManager.STREAM_MUSIC] only.
/// Make sure to call [initialize] function before using any other function.
/// Use [UniversalVolume.instance] to access the signleton instance and call any methods on it.

class UniversalVolume {
    private var isInitialized = false
    private var isVolumeChangeListenerActive = false
    private val volumeChangeListeners: MutableList<VolumeChangeListener> = ArrayList()

    private val volumeManager: VolumeInterface = if (JancarVolumeManager.isSupported()) {
        JancarVolumeManager()
    } else {
        AndroidVolumeManager()
    }

    companion object {
        private var mInstance: UniversalVolume? = null

        /// Returns the singleton instance of [UniversalVolume].
        @JvmStatic
        val instance: UniversalVolume
            get() {
                if (mInstance == null) mInstance = UniversalVolume()
                return mInstance!!
            }
    }

    /// Initializes the [UniversalVolume] instance with the given [context].
    fun initialize(context: Context) {
        if (isInitialized) return
        volumeManager.initialize(context)
        isInitialized = true
    }

    /// Disposes the [UniversalVolume] instance.
    fun dispose() {
        if (!isInitialized) return
        volumeChangeListeners.forEach {
            removeVolumeChangeListener(it)
        }
        volumeManager.dispose()
        isInitialized = false
    }

    /// Returns the current volume as an integer between 0 and [maxVolume]. The [maxVolume] depends
    // on each device.
    val volume: Int?
        get() {
            ensureInitialize()
            return volumeManager.currentVolume
        }

    /// Returns the current volume as a double between 0 and 1. The range is the same for all
    // devices
    val volumeInPercentage: Double
        get() {
            ensureInitialize()
            val currentVolume = volumeManager.currentVolume
            val maxVolume = volumeManager.maxVolume
            if (maxVolume == null || currentVolume == null) return 0.0
            return (currentVolume / maxVolume.toDouble() * 10000) / 10000
        }

    /// Sets the volume to the given [volume] value.
    /// If [showVolumeBar] is true, shows the volume bar.
    fun setVolume(volume: Int, showVolumeBar: Boolean = false): Boolean {
        ensureInitialize()
        volumeManager.setVolume(volume, showVolumeBar)
        return true
    }

    /// Sets the volume to the given [percentage] value.
    /// If [showVolumeBar] is true, shows the volume bar.
    fun setVolumeToPercentage(percentage: Double, showVolumeBar: Boolean = false): Boolean {
        ensureInitialize()
        val validPercentage = 1.0.coerceAtMost(0.0.coerceAtLeast(percentage))
        val maxVolume: Int = this.maxVolume ?: return false
        val targetVolume = (validPercentage * maxVolume).roundToInt()
        volumeManager.setVolume(targetVolume, showVolumeBar)
        return true
    }

    /// Returns the maximum volume as an integer.
    val maxVolume: Int?
        get() {
            ensureInitialize()
            return volumeManager.maxVolume
        }

    /// Returns the minimum volume as an integer.
    val minVolume: Int?
        get() {
            ensureInitialize()
            return volumeManager.minVolume
        }

    /// Adds a [listener] to the list of volume change listeners.
    fun addVolumeChangeListener(listener: VolumeChangeListener) {
        ensureInitialize()
        volumeChangeListeners.add(listener)
        if (!isVolumeChangeListenerActive) {
            isVolumeChangeListenerActive = true
            volumeManager.setVolumeChangeListener {
                volumeChangeListeners.forEach { listener ->
                    listener.onChange(it)
                }
            }
        }
    }

    fun removeVolumeChangeListener(listener: VolumeChangeListener) {
        volumeChangeListeners.remove(listener)
        if (volumeChangeListeners.isEmpty() && isVolumeChangeListenerActive) {
            volumeManager.removeVolumeChangeListener()
            isVolumeChangeListenerActive = false
        }
    }

    private fun ensureInitialize() {
        if (!isInitialized) throw Exception("VolumeControlSdk is not initialized, please call initialize() first")
    }
}
