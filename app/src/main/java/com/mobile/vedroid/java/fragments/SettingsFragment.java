package com.mobile.vedroid.java.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.databinding.FragmentSettingsBinding;

public class SettingsFragment
        extends DebuggingFragment {

    private FragmentSettingsBinding fragmentBinding;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragmentBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding = this.fragmentBinding;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        debugging("HI");

        sharedPreferences = getActivity().getSharedPreferences("SP", MODE_PRIVATE);

        fragmentBinding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear account
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // return to start
                Navigation.findNavController(view).navigate(R.id.action_screen_settings_to_start);
            }
        });

        fragmentBinding.btnCloseSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                debugging("FAB click to close");

                // return to final
                Navigation.findNavController(view).navigate(R.id.action_screen_settings_to_start);
            }
        });

        // TODO data store for language and mode
    }
}
