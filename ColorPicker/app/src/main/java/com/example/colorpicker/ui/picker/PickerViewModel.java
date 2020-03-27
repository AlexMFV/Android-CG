package com.example.colorpicker.ui.picker;

import androidx.lifecycle.ViewModel;
import android.widget.Button;
import android.widget.TextView;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;

public class PickerViewModel extends ViewModel {

    //Constructor
    public PickerViewModel() {
    }

    void UpdateColorBox(Button colorBox, ColorEnvelope color) {
        colorBox.setBackgroundColor(color.getColor());
    }

    void UpdateColorValue(TextView hex, TextView argb, ColorEnvelope color){
        hex.setText(("Hex Value: #" + color.getHexCode().substring(2)));
        int[] argbValues = color.getArgb();
        argb.setText(("RGB Value: (" + argbValues[1] + "," + argbValues[2] + "," + argbValues[3] + ")"));
    }
}