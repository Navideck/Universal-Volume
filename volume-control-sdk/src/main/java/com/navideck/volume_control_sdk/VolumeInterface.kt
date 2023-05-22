package com.navideck.volume_control_sdk

import android.content.Context


interface VolumeInterface {

    fun initialize(context: Context)

    fun dispose()

    fun setVolume(volume: Int, showVolumeBar: Boolean = false)

    fun currentVolume(): Int?

    fun maxVolume(): Int?

    fun minVolume(): Int?

    fun setVolumeChangeListener(listener: (Int) -> Unit)
}