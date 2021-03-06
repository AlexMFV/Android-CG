package com.example.colorpicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colorpicker.R.string;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView info;
    private Button signin;
    private Button btnReset;
    private int counter = 3;

    private FirebaseAuth fbAuth;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //FireBase Instances
        fbAuth = FirebaseAuth.getInstance(); // Make connection to FireBase

        email = findViewById(R.id.userName); // assigns the variable to the id in the XML layout
        password = findViewById(R.id.userPassword);
        info = findViewById(R.id.tvInfo);
        signin = findViewById(R.id.btnSignin);
        btnReset = findViewById(R.id.btnResetPwd);

        info.setText(String.format("%s %d", getString(string.text_Attempts), counter));

        //Event Handlers
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(email.getText().toString(), password.getText().toString());
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isValidEmail(email.getText().toString()))
                    sendResetEmail(email.getText().toString());
            }
        });
    }

    /**
     * This method validates if the user and password are valid before executing the Login
     * @param usr String containing the user's email
     * @param pwd String containing the user's password
     * @see FirebaseAuth
     */
    private void validate (String usr, String pwd){
        if(isValidEmail(usr) && isValidPwd(pwd))
            loginUser(usr, pwd);
        else
            checkReset();
    }

    /**
     * This method uses a Regex to determine if the inserted email is valid.
     * @param email String containing the email for verification
     * @return Boolean if email is valid or not
     * @see android.service.autofill.RegexValidator
     */
    private boolean isValidEmail(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    /**
     * This method only verifies if the password has the minimum length to be acceptable
     * @param pwd String containing the user's password
     * @return Boolean if the password is according to the requirements or not.
     */
    private boolean isValidPwd(String pwd){
        return pwd.length() >= 6;
    }

    /**
     * This method is responsible for Loggin the user in by providing a valid email and password.
     * @param usr String containing the user's email
     * @param pwd String containing the user's password
     */
    private void loginUser(String usr, String pwd){
        signin.setEnabled(false);
        fbAuth.signInWithEmailAndPassword(usr, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    checkReset();
                    signin.setEnabled(true);
                    Toast.makeText(SignIn.this, "Error logging in, please try again!", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent mainPage = new Intent(SignIn.this, MainActivity.class); //Redirect the user to the Registered MainPage
                    startActivity(mainPage);
                    finish();
                }
            }
        });
    }

    /**
     * Updates the text of the attempts made in the user Login Page
     */
    @SuppressLint("DefaultLocale")
    private void checkReset(){
        if(counter > 0){
            counter--; //Disables SignIn button after x attempts
            info.setText(String.format("%s %d", getString(string.text_Attempts), counter));
        }

        if(counter == 0){
            btnReset.setEnabled(true);
            btnReset.setVisibility(View.VISIBLE);
        }
    }

    /**
     * If the user fails to login a certain amount of time a button to reset the password is show, this method ensures the user
     * receives and email to change the password for the respective email address.
     * @param usr String containing the user's email
     * @see FirebaseAuth
     */
    private void sendResetEmail(String usr){
        btnReset.setEnabled(false);
        fbAuth.sendPasswordResetEmail(usr).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()) {
                    Toast.makeText(SignIn.this, "Please use a valid email!", Toast.LENGTH_LONG).show();
                    btnReset.setEnabled(true);
                }
                else {
                    Toast.makeText(SignIn.this, "Password reset email has been sent!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
