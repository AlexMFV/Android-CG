package com.example.colorpicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Registration extends AppCompatActivity {

    //FireBase Variables
    private FirebaseAuth fbAuth;
    private String userName;
    private String userPassword;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fbAuth = FirebaseAuth.getInstance(); //Make the connection to FireBase

        RegisterNewUser(userName, userPassword);
    }

    public void RegisterNewUser(String usr, String pwd){
        fbAuth.createUserWithEmailAndPassword(usr, pwd).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>(){
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (!task.isSuccessful()) {
					Toast.makeText(Registration.this, "There was an error creating the account!", Toast.LENGTH_LONG);
				} else {
					//Send the user to the "Logged in Activity"
					Intent RegIntent = new Intent(Registration.this, MainActivity.class);
					startActivity(RegIntent);
				}
			}
		});
	}
}