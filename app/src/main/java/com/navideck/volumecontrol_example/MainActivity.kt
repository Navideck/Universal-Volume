package com.navideck.volumecontrol_example

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.navideck.volume_control_sdk.VolumeControlSdk

@SuppressLint("SetTextI18n", "UseSwitchCompatOrMaterialCode")
class MainActivity : AppCompatActivity() {
    private lateinit var volumeControlSdk: VolumeControlSdk
    private lateinit var txtCurrentVolume: TextView
    var showVolumeBar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtCurrentVolume = findViewById(R.id.txtCurrentVolume)
        val btnIncreaseVolume: Button = findViewById(R.id.btnIncreaseVolume)
        val btnDecreaseVolume: Button = findViewById(R.id.btnDecreaseVolume)
        val txtMaxVolume: TextView = findViewById(R.id.txtMaxVolume)
        val txtMinVolume: TextView = findViewById(R.id.txtMinVolume)

        findViewById<Switch>(R.id.switchShowVolumeBar).setOnCheckedChangeListener { _, isChecked ->
            showVolumeBar = isChecked
        }

        volumeControlSdk = VolumeControlSdk.instance
        volumeControlSdk.initialize(this)
        volumeControlSdk.setVolumeChangeListener {
            updateCurrentVolumeText()
        }

        updateCurrentVolumeText()
        txtMaxVolume.text = "Max Volume : " + volumeControlSdk.maxVolume.toString()
        txtMinVolume.text = "Min Volume : " + volumeControlSdk.minVolume.toString()

        btnIncreaseVolume.setOnClickListener {
            volumeControlSdk.setVolumeInPercentage(
                volumeControlSdk.volumeInPercentage + 0.1,
                showVolumeBar
            )
            updateCurrentVolumeText()
        }

        btnDecreaseVolume.setOnClickListener {
            volumeControlSdk.setVolumeInPercentage(
                volumeControlSdk.volumeInPercentage - 0.1,
                showVolumeBar
            )
            updateCurrentVolumeText()
        }
    }


    private fun updateCurrentVolumeText() {
        txtCurrentVolume.text =
            "Current Volume : ${volumeControlSdk.volume} ( ${volumeControlSdk.volumeInPercentage * 100} % )"
    }
}