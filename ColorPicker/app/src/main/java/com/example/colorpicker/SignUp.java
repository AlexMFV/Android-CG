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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    //FireBase Variables
    private FirebaseAuth fbAuth;
    DatabaseReference usersRef;

    private EditText userName;
    private EditText userPassword;
    private EditText passwordVerif;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //FireBase Instances
        fbAuth = FirebaseAuth.getInstance(); //Make the connection to FireBase

        userName = findViewById(R.id.userName2);
        userPassword= findViewById(R.id.userPassword2);
        passwordVerif = findViewById(R.id.userPassword3);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegister.setEnabled(false);
                if(userPassword.getText().toString().equals(passwordVerif.getText().toString()))
                    RegisterNewUser(userName.getText().toString(), userPassword.getText().toString());
                else {
                    btnRegister.setEnabled(true);
                    Toast.makeText(getBaseContext(), "Passwords are not the same! Please change them and try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Once the user enters their details, this method is responsible for sending the data to  FireBase to create a new user account
     * @param usr String containing the user valid email
     * @param pwd String containing the user valid password
     */
    public void RegisterNewUser(final String usr, String pwd){
        fbAuth.createUserWithEmailAndPassword(usr, pwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>(){
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (!task.isSuccessful()) {
					Toast.makeText(SignUp.this, "There was an error creating the account!", Toast.LENGTH_LONG).show();
				} else {
				    //Get the ID of the current signed in user
				    String userID = fbAuth.getCurrentUser().getUid();
				    //Creates an instance on the FirebaseDatabase
                    usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                    //Hash map mapped to the structure of the database table
                    HashMap userMap = new HashMap();
                    userMap.put("username", CreateUsername(usr));
                    userMap.put("join_date", new Date());

                    //Creates a new field for the user to be created
                    usersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()) {
                                //Send the user to the "Logged in Activity"
                                Intent RegIntent = new Intent(SignUp.this, MainActivity.class);
                                startActivity(RegIntent);
                                finish();

                                Toast.makeText(getBaseContext(), "The account was created successfully!", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
				}
			}
		});
	}

    /**
     * Create a temporary username from the provided email address
     * @param email String containing an email address
     * @return String with a username based on the email
     */
    public String CreateUsername(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}