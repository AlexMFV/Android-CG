package com.example.colorpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void onClickStartHandler(View view){
        startActivity(new Intent(HomePage.this, MainActivity.class)); //Open the start page as a guest NOT IMPLEMENTED YET
        finish();
    }

    public void onClickHandler(View view){
        Intent myIntent = new Intent(HomePage.this, SignIn.class);
        startActivity(myIntent);
        finish();
    }
}
