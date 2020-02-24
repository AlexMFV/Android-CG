package com.example.colorpicker.ui.dashboard;

import android.media.Image;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is where the color picker goes!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}