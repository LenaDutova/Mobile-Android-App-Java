package com.mobile.vedroid.java.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.SingleActivity;
import com.mobile.vedroid.java.databinding.FragmentReturningBinding;
import com.mobile.vedroid.java.model.Account;

public class ReturningFragment
        extends DebuggingFragment {

    private FragmentReturningBinding fragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        debugging("HI");
        this.fragmentBinding = FragmentReturningBinding.inflate(inflater, container, false);
        binding = this.fragmentBinding;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText login = fragmentBinding.registrationLogin;
        MaterialButtonToggleGroup toggle = fragmentBinding.registrationGenderToggle;
        Button btnStart = fragmentBinding.btnToStart;

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                debugging("Click from returning");

                if (login.getText() != null && !login.getText().toString().isBlank()
                        && toggle.getCheckedButtonId() != R.id.btn_not_defined) {
                    var name = login.getText().toString();
                    var sex = toggle.getCheckedButtonId() == R.id.btn_man;

                    var action = ReturningFragmentDirections.actionScreenRegisterReturnStart();
                    action.setACCOUNT(new Account(name, sex));
                    Navigation.findNavController(view).navigate(action);
                } else {
                    String warning = getString(R.string.text_please);
                    if (login.getText() == null || login.getText().toString().isBlank()) warning += getString(R.string.text_no_name);
                    if (toggle.getCheckedButtonId() == R.id.btn_not_defined) warning += getString(R.string.text_no_gender);
                    ((SingleActivity) getActivity()).showSnackBar(warning);
                }
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                debugging("Back stack click");
                Navigation.findNavController(view).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}
