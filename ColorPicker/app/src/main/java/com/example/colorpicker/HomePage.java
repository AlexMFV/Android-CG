package com.example.colorpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.security.Signature;

public class HomePage extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mContext = getApplicationContext();
    }

    public void onClickStartHandler(View view){
        startActivity(new Intent(mContext, MainActivity.class)); //Open the start page as a guest NOT IMPLEMENTED YET
    }

    public void onClickRegHandler(View view){
        startActivity(new Intent(mContext, SignUp.class));
    }

    public void onClickHandler(View view){
        startActivity(new Intent(mContext, SignIn.class));
    }
}
