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

/**
 * @author Reece Ward
 * @author Adam Guiton
 */
public class SettingsFragmentGuest extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_page_guest, container, false);
        Button btnLogout = root.findViewById(R.id.btn_Back);
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                redirectMenu();
            }
        });
        return root;
    }

    /**
     * This method is responsible for sending the user to the HomePage where the user can Login or Register
     */
    private void redirectMenu(){
        Intent myIntent = new Intent(SettingsFragmentGuest.this.getActivity(), HomePage.class);
        startActivity(myIntent);
        SettingsFragmentGuest.this.getActivity().finish();
    }
}