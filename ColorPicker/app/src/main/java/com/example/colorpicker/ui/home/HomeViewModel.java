package com.example.colorpicker.ui.home;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.colorpicker.R;

import java.util.Calendar;

public class HomeViewModel extends ViewModel {

    public HomeViewModel() { }

    void loadColourOfTheDay(View root) {
        TextView cotd_description = root.findViewById(R.id.text_colour_description);
        ImageView cotd_image = root.findViewById(R.id.image_cotd);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                cotd_description.setText(R.string.text_Sunday);
                cotd_image.setColorFilter(Color.rgb(240, 150, 35));
                break;
            case Calendar.MONDAY:
                cotd_description.setText(R.string.text_Monday);
                cotd_image.setColorFilter(Color.rgb(240, 230, 220));
                break;
            case Calendar.TUESDAY:
                cotd_description.setText(R.string.text_Tuesday);
                cotd_image.setColorFilter(Color.rgb(200, 0, 0));
                break;
            case Calendar.WEDNESDAY:
                cotd_description.setText(R.string.text_Wednesday);
                cotd_image.setColorFilter(Color.rgb(90, 230, 50));
                break;
            case Calendar.THURSDAY:
                cotd_description.setText(R.string.text_Thursday);
                cotd_image.setColorFilter(Color.rgb(230, 240, 60));
                break;
            case Calendar.FRIDAY:
                cotd_description.setText(R.string.text_Friday);
                cotd_image.setColorFilter(Color.rgb(30, 20, 190));
                break;
            case Calendar.SATURDAY:
                cotd_description.setText(R.string.text_Saturday);
                cotd_image.setColorFilter(Color.rgb(240,130,240));
                break;
        }
    }
}