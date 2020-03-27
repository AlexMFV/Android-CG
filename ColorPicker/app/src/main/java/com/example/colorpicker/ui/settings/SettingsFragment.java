package com.example.colorpicker.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.colorpicker.HomePage;
import com.example.colorpicker.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsFragment;
    private Button btnLogout;
    private Button btnSwitch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsFragment = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.settingspage, container, false);
        btnLogout = root.findViewById(R.id.LogoutButton);
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                redirectMenu();
            }
        });
        btnSwitch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(btnSwitch.isChecked()){

                }
            }
        });

        return root;
    }


    public void redirectMenu(){
        Intent myIntent = new Intent(SettingsFragment.this.getActivity(), HomePage.class);
        startActivity(myIntent);
        SettingsFragment.this.getActivity().finish();
    }

