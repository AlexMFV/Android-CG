package com.example.colorpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView info;
    private Button signin;
    private int counter = 5;

    //Firebase Variables
    private FirebaseAuth  fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        fbAuth = FirebaseAuth.getInstance(); // Make connection to Firebase
        username = (EditText)findViewById(R.id.userName); // assigns the variable to the id in the XML layout
        password = (EditText)findViewById(R.id.userPassword);
        info = (TextView)findViewById(R.id.tvInfo);
        signin = (Button)findViewById(R.id.btnSignin);

        info.setText("No of attempts remaining: 5");

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(username.getText().toString(), password.getText().toString());
            }
        });

    }

    private void validate (String userName, String userPassword){
        if((userName == "Admin") && (userPassword == "1234")){
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);
        } else{
            counter--; //Disables SignIn button after 5 attempts

            info.setText("No of attempts remaining: " + String.valueOf(counter));

            if(counter == 0){

                signin.setEnabled(false);
            }
        }
    }
}
