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

    /**
     * Event to send the use to the MainActivity as a Guest User
     * @param view Variable of type View
     */
    public void onClickStartHandler(View view){
        startActivity(new Intent(mContext, MainActivityGuest.class));
    }

    /**
     * Event responsible for send the user as a recently Registered user
     * @param view Variable of type View
     */
    public void onClickRegHandler(View view){
        startActivity(new Intent(mContext, SignUp.class));
    }

    /**
     * Event responsible for send the user as a recently Logged in user
     * @param view Variable of type View
     */
    public void onClickHandler(View view){
        startActivity(new Intent(mContext, SignIn.class));
    }
}
