package com.navideck.universal_volume.volume_manager

import android.content.Context
import android.database.ContentObserver
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.navideck.universal_volume.VolumeInterface


class AndroidVolumeManager : VolumeInterface {
    private lateinit var audioManager: AudioManager
    private var context: Context? = null
    private var mVolumeChangeListener: ((Int) -> Unit)? = null
    private val audioStream = AudioManager.STREAM_MUSIC

    private val mContentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        private var mLastDetectedVolume = 0
        override fun onChange(selfChange: Boolean) {
            val currentVolume = audioManager.getStreamVolume(audioStream)
            if (currentVolume == mLastDetectedVolume) return
            mLastDetectedVolume = currentVolume
            mVolumeChangeListener?.invoke(currentVolume)
        }
    }

    override fun initialize(context: Context) {
        this.context = context
        audioManager = context.getSystemService(AppCompatActivity.AUDIO_SERVICE) as AudioManager
    }

    override fun dispose() {
        removeVolumeChangeListener()
        context = null
    }

    override fun setVolume(volume: Int, showVolumeBar: Boolean) {
        val initialVolume = this.currentVolume
        if (volume == initialVolume) return
        val volumeBarFlag = if (showVolumeBar) 1 else 0

        // First, try to set the volume using setStream
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, volumeBarFlag)
        // Check if volume changed
        if (initialVolume != this.currentVolume) return

        // Set volume using adjustStreamVolume
        if (volume > this.currentVolume) {
            while (this.currentVolume < volume) {
                audioManager.adjustStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE,
                    volumeBarFlag
                )
            }
        } else {
            while (this.currentVolume > volume) {
                audioManager.adjustStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER,
                    volumeBarFlag
                )
            }
        }
    }

    override val currentVolume: Int
        get() = audioManager.getStreamVolume(audioStream)

    override val maxVolume: Int
        get() = audioManager.getStreamMaxVolume(audioStream)

    override val minVolume: Int?
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            audioManager.getStreamMinVolume(audioStream)
        } else {
            null
        }

    override fun setVolumeChangeListener(listener: (Int) -> Unit) {
        //TODO: Use broadcast receiver instead of content observer
        context?.applicationContext?.contentResolver?.registerContentObserver(
            android.provider.Settings.System.CONTENT_URI,
            true,
            mContentObserver,
        )
        mVolumeChangeListener = listener
    }

    override fun removeVolumeChangeListener() {
        if (mVolumeChangeListener != null) {
            context?.applicationContext?.contentResolver?.unregisterContentObserver(mContentObserver)
            mVolumeChangeListener = null
        }
    }

}

