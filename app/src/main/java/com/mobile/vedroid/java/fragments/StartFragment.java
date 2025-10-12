package com.mobile.vedroid.java.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentResultListener;

import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.SingleActivity;

public class StartFragment
        extends DebuggingFragment
        implements View.OnClickListener {

    private TextView greeting;

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
        this.greeting = view.findViewById(R.id.tv_greeting);

        btnFinal.setOnClickListener(this);
        btnReturning.setOnClickListener(this);
    }

    @Override
    public void onClick(View button) {
        if (button.getId() == R.id.btn_to_final){
            debugging("Click to final");

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, FinalFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }

        if (button.getId() == R.id.btn_to_returning){
            debugging("Click to returning");
            getParentFragmentManager()
                    .beginTransaction()
                    .addToBackStack("start")
                    .add(R.id.nav_host_fragment, ReturningFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();

            getParentFragmentManager().setFragmentResultListener(
                    SingleActivity.REGISTER,
                    this,
                    new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    debugging("Listen results: " + requestKey);

                    String txt  = getString(R.string.text_greeting) + " ";
                    txt += (result.getBoolean(SingleActivity.GENDER, false)) ? getString(R.string.text_mr) : getString(R.string.text_mrs);
                    txt += " " + result.getString(SingleActivity.LOGIN) + "!";

                    greeting.setText(txt);
                }
            });
        }
    }
}