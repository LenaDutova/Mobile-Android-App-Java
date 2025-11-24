package com.mobile.vedroid.java.ui.fragments;

import static com.google.android.material.checkbox.MaterialCheckBox.STATE_CHECKED;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.storage.SPManager;
import com.mobile.vedroid.java.ui.activity.SingleActivity;
import com.mobile.vedroid.java.databinding.FragmentSettingsBinding;

public class SettingsFragment
        extends DebuggingFragment {

    private FragmentSettingsBinding fragmentBinding;
    private SPManager spManager;

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

        this.spManager = SPManager.getInstance();

        // read saves for language and mode
        if (spManager.readIsAlwaysLanguageRu()) fragmentBinding.checkboxRuLanguage.setChecked(true);
        if (spManager.readMode() != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            int mode = spManager.readMode();
            if (mode == AppCompatDelegate.MODE_NIGHT_YES) fragmentBinding.modeToggle.check(R.id.btn_dark);
            else fragmentBinding.modeToggle.check(R.id.btn_light);
        }

        fragmentBinding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear account
                spManager.clearAccount();

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

        fragmentBinding.checkboxRuLanguage.addOnCheckedStateChangedListener(new MaterialCheckBox.OnCheckedStateChangedListener() {
            @Override
            public void onCheckedStateChangedListener(@NonNull MaterialCheckBox materialCheckBox, int i) {

                if (i == STATE_CHECKED){
                    // always Russian
                    debugging("Check always Russian");
                    ((SingleActivity) getActivity()).setLocaleAlwaysRu(true);
                } else {
                    // default language
                    debugging("Uncheck always Russian, use default Locale");
                    ((SingleActivity) getActivity()).setLocaleAlwaysRu(false);
                }

                // save checkboxRuLanguage
                spManager.saveIsAlwaysLanguageRu(i == STATE_CHECKED);
            }
        });

        fragmentBinding.modeToggle.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    int mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                    if (checkedId == R.id.btn_dark){
                        debugging("Set Dark mode");
                        mode = AppCompatDelegate.MODE_NIGHT_YES;
                    } else if (checkedId == R.id.btn_light) {
                        debugging("Set Light mode");
                        mode = AppCompatDelegate.MODE_NIGHT_NO;
                    } else {
                        debugging("Use System Dark/Light mode");
                    }
                    AppCompatDelegate.setDefaultNightMode(mode);

                    // save mode
                    spManager.saveMode(mode);
                }
            }
        });
    }

}
