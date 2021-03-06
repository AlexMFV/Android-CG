package com.example.colorpicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * @author Alex Valente
 * @author Rosie Murphy
 * @author Benedita Laranjeira
 * @author Reece Ward
 * @author Furqan Khan
 * @author Adam Guiton
 */
public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the activities to fullscreen (remove the status bar)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Set the starting activity for the app
        setContentView(R.layout.activity_main);

        //FireBase Instances
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_colorPicker, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * When the activity Starts the user is automatically redirected to the HomePage since the HomePage also serves as
     * The StartPage, the MainActivity is only a Template.
     */
    @Override
    protected void onStart() {
        super.onStart();

        //GEt the current user instance
        FirebaseUser user = mAuth.getCurrentUser();

        if(user == null) {
            startActivity(new Intent(this, HomePage.class));
            finish();
        }
        else
            CheckUserExists();
    }

    /**
     * This method verifies if the user instance that is running exists in the database.
     */
    private void CheckUserExists(){
        final String userId = mAuth.getInstance().getUid();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //If the user exists in the database under "USERS"
                if(!dataSnapshot.hasChild(userId)){
                    Toast.makeText(getBaseContext(), "There was a problem connecting to the Database", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}