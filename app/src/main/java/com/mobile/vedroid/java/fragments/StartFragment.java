package com.mobile.vedroid.java.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.SingleActivity;

public class StartFragment
        extends DebuggingFragment
        implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        debugging("HI");
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnFinal  = view.findViewById(R.id.btn_to_final);
        Button btnReturning = view.findViewById(R.id.btn_to_returning);

        btnFinal.setOnClickListener(this);
        btnReturning.setOnClickListener(this);

        if (getArguments() != null && getArguments().containsKey(SingleActivity.LOGIN)){
            String txt  = getString(R.string.text_greeting) + " ";
            txt += (getArguments().getBoolean(SingleActivity.GENDER, false)) ? getString(R.string.text_mr) : getString(R.string.text_mrs);
            txt += " " + getArguments().getString(SingleActivity.LOGIN) + "!";

            TextView greeting = view.findViewById(R.id.tv_greeting);
            greeting.setText(txt);
        }
    }

    @Override
    public void onClick(View button) {
        if (button.getId() == R.id.btn_to_final){
            debugging("Click to final");
            ((SingleActivity) getActivity()).navigate(SingleActivity.JUMP_TO_FINAL, null);
        }

        if (button.getId() == R.id.btn_to_returning){
            debugging("Click to returning");
            ((SingleActivity) getActivity()).navigate(SingleActivity.JUMP_TO_RETURNING, null);
        }
    }
}