package com.mobile.vedroid.java.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.databinding.FragmentStartBinding;
import com.mobile.vedroid.java.model.Account;

public class StartFragment
        extends DebuggingFragment
        implements View.OnClickListener {

    private FragmentStartBinding fragmentBinding;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragmentBinding = FragmentStartBinding.inflate(inflater, container, false);
        binding = this.fragmentBinding;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        debugging("HI");

        sharedPreferences = getActivity().getSharedPreferences("SP", MODE_PRIVATE);

        Button btnFinal = fragmentBinding.btnToFinal;
        Button btnReturning = fragmentBinding.btnToReturning;
        FloatingActionButton fabSettings = fragmentBinding.fabBtnSettings;
        TextView greeting = fragmentBinding.tvGreeting;

        btnFinal.setOnClickListener(this);
        btnReturning.setOnClickListener(this);
        fabSettings.setOnClickListener(this);

        Account args = StartFragmentArgs.fromBundle(getArguments()).getACCOUNT();
        if (args != null) {
            // read returned user account from ReturningFragment
            greeting.setText(createGreeting(args));

            // save new user account into SharedPreferences
            SharedPreferences.Editor prefEditor = sharedPreferences.edit();
            prefEditor.putString("ACCOUNT", args.getLogin());
            prefEditor.putBoolean("SEX", args.isGender());
            prefEditor.apply();

            debugging("Save new user account into SharedPreferences");
        } else {
            // read saved user account from SharedPreferences, if exists
            if (sharedPreferences.contains("ACCOUNT")){
                String login = sharedPreferences.getString("ACCOUNT", "");
                boolean gender = sharedPreferences.getBoolean("SEX", false);
                greeting.setText(createGreeting(login, gender));

                debugging("Read saved user account from SharedPreferences");
            } else debugging("No user account");
        }
    }

    @Override
    public void onClick(View button) {
        if (button.getId() == R.id.btn_to_final){
            debugging("Click to final");
            Navigation.findNavController(button).navigate(R.id.action_screen_start_to_final);
            return;
        }
        if (button.getId() == R.id.btn_to_returning){
            debugging("Click to returning");
            Navigation.findNavController(button).navigate(R.id.action_screen_start_to_register);
        }

        if (button.getId() == R.id.fab_btn_settings){
            debugging("FAB click");
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