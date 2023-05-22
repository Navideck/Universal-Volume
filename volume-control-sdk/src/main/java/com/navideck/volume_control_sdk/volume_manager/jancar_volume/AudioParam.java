package com.navideck.volume_control_sdk.volume_manager.jancar_volume;


public class AudioParam {
    public int mDefault;
    public int mId;
    public int mMax;
    public int mMin;
    public int mValue;

    public AudioParam(int i, int i2, int i3, int i4) {
        this.mId = i;
        this.mMin = i2;
        this.mMax = i3;
        this.mDefault = i4;
        this.mValue = i4;
    }

    public boolean set(int i) {
        if (i < this.mMin) i = this.mMin;
        if (this.mValue == i) return false;
        this.mValue = i;
        return true;
    }
}

