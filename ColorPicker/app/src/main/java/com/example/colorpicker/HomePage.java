package com.example.colorpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import com.example.colorpicker.ui.login.SignInActivity;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void onClickHandler(View view){
        Intent myintent=new Intent(HomePage.this, SignIn.class);
        startActivity(myintent);
    }
}
