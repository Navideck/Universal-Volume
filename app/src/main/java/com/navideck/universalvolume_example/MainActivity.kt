package com.navideck.universalvolume_example

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.navideck.universal_volume.UniversalVolume
import com.navideck.universal_volume.VolumeChangeListener

@SuppressLint("SetTextI18n", "UseSwitchCompatOrMaterialCode")
class MainActivity : AppCompatActivity() {
    private lateinit var universalVolume: UniversalVolume
    private lateinit var txtCurrentVolume: TextView
    private var showVolumeBar = false

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

        findViewById<Button>(R.id.btnNavigate).setOnClickListener {
            startActivity(Intent(this, MainActivityJava::class.java))
        }

        universalVolume = UniversalVolume.instance
        universalVolume.initialize(this)
        val volumeChangeListener = object : VolumeChangeListener {
            override fun onChange(volume: Int) {
                updateCurrentVolumeText()
            }
        }
        universalVolume.addVolumeChangeListener(volumeChangeListener)


        updateCurrentVolumeText()
        txtMaxVolume.text = "Max Volume : " + universalVolume.maxVolume.toString()
        txtMinVolume.text = "Min Volume : " + universalVolume.minVolume.toString()

        btnIncreaseVolume.setOnClickListener {
            universalVolume.setVolumeToPercentage(
                universalVolume.volumeInPercentage + 0.1,
                showVolumeBar
            )
            updateCurrentVolumeText()
        }

        btnDecreaseVolume.setOnClickListener {
            universalVolume.setVolumeToPercentage(
                universalVolume.volumeInPercentage - 0.1,
                showVolumeBar
            )
            updateCurrentVolumeText()
        }
    }


    private fun updateCurrentVolumeText() {
        txtCurrentVolume.text =
            "Current Volume : ${universalVolume.volume} ( ${universalVolume.volumeInPercentage * 100} % )"
    }
}