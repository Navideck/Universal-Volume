package com.navideck.universal_volume

import android.content.Context
import com.navideck.universal_volume.volume_manager.AndroidVolumeManager
import com.navideck.universal_volume.volume_manager.JancarVolumeManager
import kotlin.math.roundToInt


/// UniversalVolume manages media volume [AudioManager.STREAM_MUSIC] only
/// Use [UniversalVolume.instance] to control volume
/// Make sure to call initialize function before using any other function
/// check isInitialized to make sure initialize function is called
class UniversalVolume {
    private var isInitialized = false
    private val volumeManager: VolumeInterface = if (JancarVolumeManager.isSupported()) {
        JancarVolumeManager()
    } else {
        AndroidVolumeManager()
    }

    companion object {
        private var mInstance: UniversalVolume? = null
        val instance: UniversalVolume
            get() {
                if (mInstance == null) mInstance = UniversalVolume()
                return mInstance!!
            }
    }

    fun initialize(context: Context) {
        if (isInitialized) return
        volumeManager.initialize(context)
        isInitialized = true
    }

    fun dispose() {
        if (!isInitialized) return
        volumeManager.dispose()
        isInitialized = false
    }

    /// [volume] returns real value between 0 to MAX_VOLUME ( max_volume might be different in different devices)
    val volume: Int?
        get() {
            ensureInitialize()
            return volumeManager.currentVolume()
        }

    /// [volumeInPercentage] returns volume between 0 and 1 for all devices
    val volumeInPercentage: Double
        get() {
            ensureInitialize()
            val currentVolume = volumeManager.currentVolume()
            val maxVolume = volumeManager.maxVolume()
            if (maxVolume == null || currentVolume == null) return 0.0
            return (currentVolume / maxVolume.toDouble() * 10000) / 10000
        }

    fun setVolume(volume: Int, showVolumeBar: Boolean = false): Boolean {
        ensureInitialize()
        volumeManager.setVolume(volume, showVolumeBar)
        return true
    }

    fun setVolumeInPercentage(volume: Double, showVolumeBar: Boolean = false): Boolean {
        ensureInitialize()
        val validPercentage = 1.0.coerceAtMost(0.0.coerceAtLeast(volume))
        val maxVolume: Int = this.maxVolume ?: return false
        val targetVolume = (validPercentage * maxVolume).roundToInt()
        volumeManager.setVolume(targetVolume, showVolumeBar)
        return true
    }

    val maxVolume: Int?
        get() {
            ensureInitialize()
            return volumeManager.maxVolume()
        }

    val minVolume: Int?
        get() {
            ensureInitialize()
            return volumeManager.minVolume()
        }

    fun setVolumeChangeListener(listener: (Int) -> Unit) {
        ensureInitialize()
        //TODO: add capability to set multiple listeners
        volumeManager.setVolumeChangeListener(listener)
    }


    private fun ensureInitialize() {
        if (!isInitialized) throw Exception("VolumeControlSdk is not initialized, please call initialize() first")
    }

}