package com.example.colorpicker.ui.picker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

public class PickerFragment extends Fragment {

    private PickerViewModel pickerViewModel;
    private Button colorBox;
    private TextView hexValue;
    private TextView argbValue;
    private int palette_number = 2;

    private Colour newColour;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        pickerViewModel = ViewModelProviders.of(this).get(PickerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_picker, container, false);

        SetEventListeners(root);

        return root;
    }

    /**
     * This method is responsible for setting all the necessary Event Handlers for the ColorPicker
     * @param root
     */
    private void SetEventListeners(final View root){
        //Get the reference to the color picker and the color box
        ColorPickerView colorPicker = root.findViewById(R.id.colorPickerView);
        colorBox = root.findViewById(R.id.btnSelectedColor);
        hexValue = root.findViewById(R.id.txtHexValue);
        argbValue = root.findViewById(R.id.txtArgbValue);
        BrightnessSlideBar sliderBar = root.findViewById(R.id.BrightnessSlideBar);
        Button generate = root.findViewById(R.id.btnGenerate);
        RadioGroup rGroup = root.findViewById(R.id.radioGroup);

        //Attach Slider Bars to the ColorPickerView
        colorPicker.attachBrightnessSlider(sliderBar);

        //Event for when a new color is picked
        colorPicker.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope _envelope, boolean fromUser) {
                //Updates the values every time the selected colour changes
                pickerViewModel.UpdateColorBox(colorBox, _envelope);
                pickerViewModel.UpdateColorValue(hexValue, argbValue, _envelope);
                newColour = new Colour(_envelope.getArgb()[1], _envelope.getArgb()[2], _envelope.getArgb()[3]);
            }
        });

        /**
         * This Event is responsible for running the Generate Colour Palette code.
         */
        generate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*Generate Send the Selected Colour and the number of colours in the palette to
                * The colour generator page*/

                ColourScheme scheme = Algorithms.Random(newColour, palette_number);
                Intent intent = new Intent(getContext(), Share.class);
                //Send the palette to the next activity
                intent.putExtra("Generated_Scheme", new Gson().toJson(scheme));
                startActivity(intent);
            }
        });

        //Set the amount of colour to be generated in the palette
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton btn = root.findViewById(checkedId);
                palette_number = Integer.valueOf(btn.getText().toString());
            }
        });
    }

}