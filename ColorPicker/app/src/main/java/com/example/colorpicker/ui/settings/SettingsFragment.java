package com.example.colorpicker.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.colorpicker.HomePage;
import com.example.colorpicker.R;
import com.example.colorpicker.ViewSchemes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsFragment;
    private Button btnLogout;
    private Button btnResetPwd;
    private Button btnViewSchemes;

    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsFragment = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.settings_page, container, false);

        mAuth = FirebaseAuth.getInstance();

        btnViewSchemes = root.findViewById(R.id.ViewSchemes);
        btnViewSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewSchemes.class));
            }
        });

        btnResetPwd = root.findViewById(R.id.ChangeButton);
        btnResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnResetPwd.setEnabled(false);
                String userEmail = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
                mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getContext(), "There was an error sending the reset email! Please try again.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Email sent successfully!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        btnLogout = root.findViewById(R.id.LogoutButton);
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                redirectMenu();
            }
        });

        return root;
    }

    private void redirectMenu(){
        Intent myIntent = new Intent(SettingsFragment.this.getActivity(), HomePage.class);
        startActivity(myIntent);
        SettingsFragment.this.getActivity().finish();
    }
}

