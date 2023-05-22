package com.navideck.universalvolume_example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.navideck.universal_volume.UniversalVolume;

public class MainActivityJava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java);

        UniversalVolume universalVolume = UniversalVolume.getInstance();

        Button btnIncreaseVolume = this.findViewById(R.id.btnIncreaseVolume);
        Button btnDecreaseVolume = this.findViewById(R.id.btnDecreaseVolume);


        btnIncreaseVolume.setOnClickListener(v -> {
            double percentage = universalVolume.getVolumeInPercentage() + 0.1;
            universalVolume.setVolumeToPercentage(percentage, true);
        });

        btnDecreaseVolume.setOnClickListener(v -> {
            double percentage = universalVolume.getVolumeInPercentage() - 0.1;
            universalVolume.setVolumeToPercentage(percentage, true);
        });

    }
}