package com.navideck.universal_volume

/**
 * This interface defines a listener for volume changes.
 */
interface VolumeChangeListener {
    /**
     * This function is called when the volume is changed.
     * @param volume The new volume value.
     */
    fun onChange(volume: Int)
}