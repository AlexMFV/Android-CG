package com.example.colorpicker.ui.picker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.colorpicker.R;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class PickerFragment extends Fragment {

    private PickerViewModel pickerViewModel;
    private ColorPickerView colorPicker;
    private Button colorBox;
    private TextView hexValue;
    private TextView argbValue;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        pickerViewModel = ViewModelProviders.of(this).get(PickerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_picker, container, false);

        SetEventListeners(root);

        return root;
    }

    //Set all the necessary Event Handlers for the ColorPicker
    public void SetEventListeners(View root){
        //Get the reference to the color picker and the color box
        colorPicker = root.findViewById(R.id.colorPickerView);
        colorBox = root.findViewById(R.id.btnSelectedColor);
        hexValue = root.findViewById(R.id.txtHexValue);
        argbValue = root.findViewById(R.id.txtArgbValue);

        //Event for when a new color is picked
        colorPicker.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                pickerViewModel.UpdateColorBox(colorBox, envelope);
                pickerViewModel.UpdateColorValue(hexValue, argbValue, envelope);
            }
        });
    }

}