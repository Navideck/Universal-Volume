package com.navideck.universal_volume.volume_manager

import android.content.Context
import android.util.Log
import com.navideck.universal_volume.VolumeInterface
import com.navideck.universal_volume.helper.VolumeHelper
import com.navideck.universal_volume.volume_manager.jancar_volume.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/// JancarVolumeManager is a class that implements the VolumeInterface for the Jancar volume control system of headUnit
/// calling initialize() will connect to the JanCarService and set up the volume control system
/// connectionUpdate is a callback that is called when the connection to the JanCarService is updated
class JancarVolumeManager : VolumeInterface {
    private var mMasterVolume: AudioParam? = null
    private var janAudioManager: JanAudioManager? = null
    private val masterVolumeId = 10
    private val onConnectionUpdate = { isConnected: Boolean ->
        Log.e("JancarVolumeManager", "JanCarServiceConnected: $isConnected")
        mMasterVolume = if (isConnected) {
            janAudioManager?.getParam(masterVolumeId)
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

    override fun currentVolume(): Int? {
        return janAudioManager?.getCurrentValue(masterVolumeId)
    }

    override fun maxVolume(): Int? {
        return mMasterVolume?.mMax
    }

    override fun minVolume(): Int? {
        return mMasterVolume?.mMin
    }

    override fun setVolumeChangeListener(listener: (Int) -> Unit) {
        //TODO: implement setVolumeChangeListener
    }

}