package com.example.colorpicker.ui.picker;

import android.content.Intent;
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
import com.example.colorpicker.Share;
import com.example.colorpicker.ui.colour.Colour;
import com.example.colorpicker.ui.colour.ColourScheme;
import com.example.colorpicker.ui.common.Algorithms;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

public class PickerFragment extends Fragment {

    private PickerViewModel pickerViewModel;
    private ColorPickerView colorPicker;
    private Button colorBox;
    private TextView hexValue;
    private TextView argbValue;
    private BrightnessSlideBar sliderBar;
    private Button generate;
    private int palette_number = 2;

    Colour newColour;

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
        sliderBar = root.findViewById(R.id.BrightnessSlideBar);
        generate = root.findViewById(R.id.btnGenerate);

        colorPicker.attachBrightnessSlider(sliderBar);

        //Event for when a new color is picked
        colorPicker.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope _envelope, boolean fromUser) {
                pickerViewModel.UpdateColorBox(colorBox, _envelope);
                pickerViewModel.UpdateColorValue(hexValue, argbValue, _envelope);
                newColour = new Colour(_envelope.getArgb()[1], _envelope.getArgb()[2], _envelope.getArgb()[3]);
            }
        });

        /*Event for when the Generate Palette button is pressed*/
        generate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Generate Send the Selected Colour and the number of colours in the palette to
                * The colour generator page*/

                ColourScheme scheme = Algorithms.Random(newColour, 4);
                Intent intent = new Intent(getContext(), Share.class);
                intent.putExtra("Generated_Scheme", new Gson().toJson(scheme));
                startActivity(intent);
            }
        });
    }

}