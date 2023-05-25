package com.navideck.universal_volume

import android.content.Context


interface VolumeInterface {
    val currentVolume: Int?

    val maxVolume: Int?

    val minVolume: Int?

    fun initialize(context: Context)

    fun dispose()

    fun setVolume(volume: Int, showVolumeBar: Boolean = false)

    fun setVolumeChangeListener(listener: (Int) -> Unit)

    fun removeVolumeChangeListener()
}
