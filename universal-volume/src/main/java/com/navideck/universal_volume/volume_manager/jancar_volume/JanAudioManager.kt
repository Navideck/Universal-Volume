package com.navideck.universal_volume.volume_manager.jancar_volume

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log

class  JanAudioManager(
    context: Context,
    private val onConnectionUpdate: (isConnected: Boolean) -> Unit,
) {
    private val packageIVIService = "com.jancar.services"
    private val audioAction = "com.jancar.services.action.audio"
    private var mContext: Context = context.applicationContext
    private var mAudioInterface: IAudio? = null
    private var mServiceConnection: ServiceConnection? = null
    var mIsConnected = false

    fun connect() {
        if (mIsConnected) return
        mServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(c: ComponentName, iBinder: IBinder) {
                mAudioInterface = IAudio.Stub.asInterface(iBinder)
                mIsConnected = true
                onConnectionUpdate(true)
            }

            override fun onServiceDisconnected(c: ComponentName) {
                mIsConnected = false
                mAudioInterface = null
                onConnectionUpdate(false)
            }
        }
        mContext.bindService(
            Intent().apply {
                action = audioAction
                setPackage(packageIVIService)
            },
            mServiceConnection!!,
            Context.BIND_AUTO_CREATE,
        )
    }

    fun disconnect() {
        mAudioInterface = null
        if (mIsConnected) {
            mServiceConnection?.let { mContext.unbindService(it) }
        }
        mServiceConnection = null
        mIsConnected = false
    }

    fun setParam(mId: Int, mValue: Int) {
        try {
            mAudioInterface?.setParam(mId, mValue)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    fun getParam(mId: Int): AudioParam? {
        val iAudio = mAudioInterface ?: return null
        try {
            if (!iAudio.isParamAvailable(mId)) return null
            return AudioParam(
                mId,
                iAudio.getParamMinValue(mId),
                iAudio.getParamMaxValue(mId),
                iAudio.getParam(mId)
            )
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return null
    }

    fun getCurrentValue(i: Int): Int? {
        return try {
            mAudioInterface?.getParam(i)
        } catch (e: RemoteException) {
            Log.e("JancarVolumeManager", "Error getting current value")
            null
        }
    }

}
