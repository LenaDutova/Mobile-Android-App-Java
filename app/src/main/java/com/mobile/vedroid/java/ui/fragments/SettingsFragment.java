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
import com.mobile.vedroid.java.storage.DSManager;
import com.mobile.vedroid.java.storage.SPManager;
import com.mobile.vedroid.java.ui.activity.SingleActivity;
import com.mobile.vedroid.java.databinding.FragmentSettingsBinding;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class SettingsFragment
        extends DebuggingFragment {

    private FragmentSettingsBinding fragmentBinding;
    private SPManager spManager;
    private DSManager dsManager;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragmentBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding = this.fragmentBinding;
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        disposables.clear();
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        debugging("HI");

        this.spManager = SPManager.getInstance();
        this.dsManager = DSManager.getInstance();

        fragmentBinding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spManager.clearAccount(); // clear account
                // return to start
                Navigation.findNavController(view).navigate(R.id.action_screen_settings_to_start);
            }
        });
        fragmentBinding.btnCloseSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                disposables.add(dsManager.saveAlwaysRuLanguage(i == STATE_CHECKED).subscribe(
                                preferences -> debugging("Successfully saved AlwaysLanguageRu value " + preferences.get(DSManager.DataStoreKeys.IS_ALWAYS_RU)),
                                throwable -> debugging("Error saving AlwaysLanguageRu: " + throwable)
                        )
                );
            }
        });

        fragmentBinding.modeToggle.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    int mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                    if (checkedId == R.id.btn_dark){
                        debugging("Checked Dark mode");
                        mode = AppCompatDelegate.MODE_NIGHT_YES;
                    } else if (checkedId == R.id.btn_light) {
                        debugging("Checked Light mode");
                        mode = AppCompatDelegate.MODE_NIGHT_NO;
                    } else {
                        debugging("Checked System Dark/Light mode");
                    }
                    AppCompatDelegate.setDefaultNightMode(mode);

                    // save mode
                    disposables.add(
                            dsManager.saveLightOrNightMode(mode).subscribe(
                                    preferences -> debugging("Successfully saved LightOrNightMode value " + preferences.get(DSManager.DataStoreKeys.MODE)),
                                    throwable -> debugging("Error saving LightOrNightMode: " + throwable)
                            )
                    );
                }
            }
        });

        // read saves for language and mode
        disposables.add(
                dsManager.isAlwaysRuLanguage().subscribe(
                        language -> {
                            fragmentBinding.checkboxRuLanguage.setChecked(language);
                            debugging("Read saved AlwaysRuLanguage " + language);
                        },
                        throwable -> debugging("Error in load AlwaysRuLanguage: " + throwable)
                )
        );
        disposables.add(dsManager.loadLightOrNightMode().subscribe(
                        mode -> {
                            if (mode == AppCompatDelegate.MODE_NIGHT_YES) fragmentBinding.modeToggle.check(R.id.btn_dark);
                            else if (mode == AppCompatDelegate.MODE_NIGHT_NO) fragmentBinding.modeToggle.check(R.id.btn_light);
                            else fragmentBinding.modeToggle.check(R.id.btn_system);
                            debugging("Read saved LightOrNightMode " + mode);
                        },
                        throwable -> debugging("Error in load LightOrNightMode: " + throwable)
                )
        );
    }

}
