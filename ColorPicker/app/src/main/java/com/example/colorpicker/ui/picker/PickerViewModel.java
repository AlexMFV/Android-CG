package com.example.colorpicker.ui.picker;

import androidx.lifecycle.ViewModel;
import android.widget.Button;
import android.widget.TextView;

import com.skydoves.colorpickerview.ColorEnvelope;

public class PickerViewModel extends ViewModel {

    //Constructor
    public PickerViewModel() {
    }

    public void UpdateColorBox(Button colorBox, ColorEnvelope color) {
        colorBox.setBackgroundColor(color.getColor());
    }

    public void UpdateColorValue(TextView hex, TextView argb, ColorEnvelope color){
        hex.setText(("Hex Value: #" + color.getHexCode().substring(2)));
        argb.setText(("Argb Value: (" + color.getArgb() + ")"));
    }
}