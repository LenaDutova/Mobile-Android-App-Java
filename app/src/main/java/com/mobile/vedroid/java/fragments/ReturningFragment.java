package com.mobile.vedroid.java.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mobile.vedroid.java.DebuggingActivity;
import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.SingleActivity;

public class ReturningFragment extends Fragment {

    private TextInputEditText login;
    private MaterialButtonToggleGroup toggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((DebuggingActivity) getActivity()).debugging("HI");
        return inflater.inflate(R.layout.fragment_returning, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        login = view.findViewById(R.id.login);
        toggle = view.findViewById(R.id.toggle_button);

        Button btnStart = view.findViewById(R.id.btn_to_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DebuggingActivity) getActivity()).debugging("Click to final");

                if (login.getText() == null || login.getText().toString().isBlank()) {
                    Snackbar.make(view, "Enter name", Snackbar.LENGTH_LONG).show();
                } else {
                    if (toggle.getCheckedButtonId() == R.id.btn_not_defined) {
                        Snackbar.make(view, "Choose gender", Snackbar.LENGTH_LONG).show();
                    } else {
                        Bundle intent = new Bundle();
                        intent.putString(DebuggingActivity.LOGIN, login.getText().toString());
                        intent.putBoolean(DebuggingActivity.GENDER, (toggle.getCheckedButtonId() == R.id.btn_man));
                        ((SingleActivity) getActivity()).navigate(DebuggingActivity.JUMP_FROM_RETURNING, intent);
                    }
                }
            }
        });
    }
}
