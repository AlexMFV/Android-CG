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

public class SettingsFragmentGuest extends Fragment {

    private SettingsViewModel settingsFragment;
    private Button btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsFragment = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.settings_page_guest, container, false);
        btnLogout = root.findViewById(R.id.btn_Back);
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                redirectMenu();
            }
        });
        return root;
    }

    public void redirectMenu(){
        Intent myIntent = new Intent(SettingsFragmentGuest.this.getActivity(), HomePage.class);
        startActivity(myIntent);
        SettingsFragmentGuest.this.getActivity().finish();
    }
}