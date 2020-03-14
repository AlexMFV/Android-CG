package com.example.colorpicker;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.widget.TextView;
import android.graphics.*;
import android.widget.ImageView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView cotd_description;
    private ImageView cotd_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the activities to fullscreen (remove the status bar)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_colorPicker)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        loadColourOfTheDay();
    }

    public void loadColourOfTheDay() {
        cotd_description = (TextView) findViewById(R.id.text_colour_description);
        cotd_image = (ImageView) findViewById(R.id.image_cotd);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                cotd_description.setText("Today's colour of the day is Orange. This colour is known to represent an adventurous nature and joy or perhaps a time of change.");
                cotd_image.setColorFilter(Color.rgb(240, 150, 35));
                break;
            case Calendar.MONDAY:
                cotd_description.setText("Today's colour of the day is White. This colour is known to represent purity, innocence and cleanliness.");
                cotd_image.setColorFilter(Color.rgb(240, 230, 220));
                break;
            case Calendar.TUESDAY: 
                cotd_description.setText("Today's colour of the day is Red. This colour is known to represent energy, strength, power and desire.");
                cotd_image.setColorFilter(Color.rgb(200, 0, 0));
                break;
            case Calendar.WEDNESDAY: 
                cotd_description.setText("Today's colour of the day is Green. This colour is known to represent wealth, renewal and to be the colour of nature.");
                cotd_image.setColorFilter(Color.rgb(90, 230, 50));
                break;
            case Calendar.THURSDAY:
                cotd_description.setText("Today's colour is Light Yellow. This colour is known to represent insight, enlightenment and happiness.");
                cotd_image.setColorFilter(Color.rgb(230, 240, 60));
                break;
            case Calendar.FRIDAY:
                cotd_description.setText("Today's colour is Blue. This colour is known to represent tranquility, calmness and stability, often associated with the sky and sea.");
                cotd_image.setColorFilter(Color.rgb(30, 20, 190));
                break;
            case Calendar.SATURDAY:
                cotd_description.setText("Today's colour is Violet. This colour is known to represent creativity, inspiration and ambition.");
                cotd_image.setColorFilter(Color.rgb(240,130,240));
                break;
        }
    }


}
