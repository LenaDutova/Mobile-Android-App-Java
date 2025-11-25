package com.mobile.vedroid.java.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.Navigation;

import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.storage.DSManager;
import com.mobile.vedroid.java.storage.SPManager;
import com.mobile.vedroid.java.ui.activity.SingleActivity;
import com.mobile.vedroid.java.databinding.FragmentStartBinding;
import com.mobile.vedroid.java.model.Account;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class StartFragment
        extends DebuggingFragment
        implements View.OnClickListener {

    private FragmentStartBinding fragmentBinding;
    private SPManager spManager;
    private DSManager dsManager;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragmentBinding = FragmentStartBinding.inflate(inflater, container, false);
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

        fragmentBinding.btnToFinal.setOnClickListener(this);
        fragmentBinding.btnToReturning.setOnClickListener(this);
        fragmentBinding.fabBtnSettings.setOnClickListener(this);

        Account args = StartFragmentArgs.fromBundle(getArguments()).getACCOUNT();
        if (args != null) {
            // read returned user account from ReturningFragment
            fragmentBinding.tvGreeting.setText(createGreeting(args));

            // save new user account into SharedPreferences
            spManager.saveAccount(args);
            debugging("Save new user account into SharedPreferences");
        } else {
            // read saved user account from SharedPreferences, if exists
            if (spManager.hasAccount()){
                args = spManager.readAccount();
                fragmentBinding.tvGreeting.setText(createGreeting(args));
                debugging("Read saved user account from SharedPreferences");
            } else debugging("No user account");
        }

        disposables.add(dsManager.isAlwaysRuLanguage()
                .subscribe(
                        language -> {
                            debugging("Read saved AlwaysRuLanguage: " + language);
                            if (language) ((SingleActivity) getActivity()).setLocaleAlwaysRu(true);
                        },
                        throwable -> debugging("Error in load AlwaysRuLanguage: " + throwable)
                )
        );
        disposables.add(dsManager.loadLightOrNightMode().subscribe(
                        mode -> {
                            debugging("Read saved LightOrNightMode: " + mode);
                            if (mode != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) AppCompatDelegate.setDefaultNightMode(mode);
                        },
                        throwable -> debugging("Error in load LightOrNightMode: " + throwable)
                )
        );
    }

    @Override
    public void onClick(View button) {
        if (button.getId() == R.id.btn_to_final){
            debugging("Click to main content screen (FinalFragment)");
            Navigation.findNavController(button).navigate(R.id.action_screen_start_to_final);
            return;
        }
        if (button.getId() == R.id.btn_to_returning){
            debugging("Click to registration screen (ReturningFragment)");
            Navigation.findNavController(button).navigate(R.id.action_screen_start_to_register);
        }

        if (button.getId() == R.id.fab_btn_settings){
            debugging("Click to settings screen (SettingsFragment)");
            Navigation.findNavController(button).navigate(R.id.action_screen_start_to_settings);
        }
    }


    private String createGreeting (Account user){
        return createGreeting(user.getLogin(), user.isGender());
    }

    private String createGreeting (String login, boolean gender){
        StringBuilder txt  = new StringBuilder(getString(R.string.text_greeting))
                .append(" ")
                .append(gender ? getString(R.string.text_mr) : getString(R.string.text_mrs))
                .append(" ")
                .append(login)
                .append("!");
        return txt.toString();
    }
}