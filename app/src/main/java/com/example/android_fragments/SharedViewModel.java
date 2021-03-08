package com.example.android_fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<Character> charInfo = new MutableLiveData<>();
    private MutableLiveData<Episode> epInfo = new MutableLiveData<>();
    private MutableLiveData<List<Location>> locInfo = new MutableLiveData<>();

    public void setCharInfo(Character charInfo) {
        this.charInfo.setValue(charInfo);
    }

    public MutableLiveData<Character> getCharInfo() {
        return charInfo;
    }

    public void setEpInfo(Episode epInfo) {
        this.epInfo.setValue(epInfo);
    }

    public MutableLiveData<Episode> getEpInfo() {
        return epInfo;
    }

    public void setLocInfo(List<Location> locInfo) {
        this.locInfo.setValue(locInfo);
    }

    public MutableLiveData<List<Location>> getLocInfo() {
        return locInfo;
    }
}