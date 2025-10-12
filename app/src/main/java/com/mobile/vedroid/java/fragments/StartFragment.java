package com.mobile.vedroid.java.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.databinding.FragmentStartBinding;
import com.mobile.vedroid.java.model.Account;

public class StartFragment
        extends DebuggingFragment
        implements View.OnClickListener {

    private FragmentStartBinding fragmentBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        debugging("HI");
        this.fragmentBinding = FragmentStartBinding.inflate(inflater, container, false);
        binding = this.fragmentBinding;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnFinal = fragmentBinding.btnToFinal;
        Button btnReturning = fragmentBinding.btnToReturning;
        TextView greeting = fragmentBinding.tvGreeting;

        btnFinal.setOnClickListener(this);
        btnReturning.setOnClickListener(this);

        Account args = StartFragmentArgs.fromBundle(getArguments()).getACCOUNT();
        if (args != null) {
            String txt  = getString(R.string.text_greeting) + " ";
            txt += args.isGender() ? getString(R.string.text_mr) : getString(R.string.text_mrs);
            txt += " " + args.getLogin() + "!";
            debugging("text " + txt);

            greeting.setText(txt);
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
    }
}