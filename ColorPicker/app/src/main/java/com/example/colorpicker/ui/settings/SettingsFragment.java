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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_page, container, false);
        Button btnLogout = root.findViewById(R.id.LogoutButton);
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