package com.example.colorpicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    //FireBase Variables
    private FirebaseAuth fbAuth;
    private EditText userName;
    private EditText userPassword;
    private EditText passwordVerif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fbAuth = FirebaseAuth.getInstance(); //Make the connection to FireBase
        userName = findViewById(R.id.userName2);
        userPassword= findViewById(R.id.userPassword2);
        passwordVerif = findViewById(R.id.userPassword3);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPassword.getText().toString().equals(passwordVerif.getText().toString()))
                    RegisterNewUser(userName.getText().toString(), userPassword.getText().toString());
            }
        });
    }

    public void RegisterNewUser(String usr, String pwd){
        fbAuth.createUserWithEmailAndPassword(usr, pwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>(){
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (!task.isSuccessful()) {
					Toast.makeText(SignUp.this, "There was an error creating the account!", Toast.LENGTH_LONG).show();
				} else {
					//Send the user to the "Logged in Activity"
					Intent RegIntent = new Intent(SignUp.this, MainActivity.class);
					startActivity(RegIntent);
					finish();
				}
			}
		});
	}
}