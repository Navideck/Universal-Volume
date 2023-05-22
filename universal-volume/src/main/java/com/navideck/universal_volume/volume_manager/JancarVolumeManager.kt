package com.navideck.universal_volume.volume_manager

import android.content.Context
import android.util.Log
import com.navideck.universal_volume.helper.VolumeInterface
import com.navideck.universal_volume.helper.VolumeHelper
import com.navideck.universal_volume.volume_manager.jancar_volume.*

/// JancarVolumeManager is a class that implements the VolumeInterface for the Jancar volume control system of headUnit
/// calling initialize() will connect to the JanCarService and set up the volume control system
/// connectionUpdate is a callback that is called when the connection to the JanCarService is updated
internal class JancarVolumeManager : VolumeInterface {
    // MasterVolume (10) is equivalent of AudioManager.STREAM_MUSIC
    private val audioStream = 10
    private var mMasterVolume: AudioParam? = null
    private var janAudioManager: JanAudioManager? = null
    private val onConnectionUpdate = { isConnected: Boolean ->
        Log.e("JancarVolumeManager", "JanCarServiceConnected: $isConnected")
        mMasterVolume = if (isConnected) {
            janAudioManager?.getParam(audioStream)
        } else {
            null
        }
    }

    companion object {
        fun isSupported(): Boolean {
            val supportedDevices = arrayListOf("ac8227l", "ac8257")
            return supportedDevices.contains(VolumeHelper().buildPlatform)
        }
    }

    override fun initialize(context: Context) {
        janAudioManager = JanAudioManager(context, onConnectionUpdate)
        janAudioManager?.connect()
    }

    override fun dispose() {
        janAudioManager?.disconnect()
    }

    override fun setVolume(volume: Int, showVolumeBar: Boolean) {
        //TODO: implement showVolumeBar
        mMasterVolume?.set(volume)
        mMasterVolume?.let {
            janAudioManager?.setParam(it.mId, it.mValue)
        }
    }

    override val currentVolume: Int?
        get() = janAudioManager?.getCurrentValue(audioStream)

    override val maxVolume: Int?
        get() = mMasterVolume?.mMax

    override val minVolume: Int?
        get() = mMasterVolume?.mMin

    override fun setVolumeChangeListener(listener: (Int) -> Unit) {
        //TODO: implement setVolumeChangeListener
    }

    override fun removeVolumeChangeListener() {
        //TODO: implement removeVolumeChangeListener
    }


}