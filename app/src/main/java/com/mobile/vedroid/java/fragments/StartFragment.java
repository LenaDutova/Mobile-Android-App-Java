package com.mobile.vedroid.java.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.mobile.vedroid.java.DebuggingActivity;
import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.SingleActivity;

public class StartFragment
        extends Fragment
        implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((DebuggingActivity) getActivity()).debugging("HI");
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnFinal  = view.findViewById(R.id.btn_to_final);
        Button btnReturning = view.findViewById(R.id.btn_to_returning);

        btnFinal.setOnClickListener(this);
        btnReturning.setOnClickListener(this);
    }

//    ActivityResultLauncher<Intent> resultLauncher;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_start);
//
//
//        ((DebuggingActivity) getActivity()).debugging("HI");
//
//        Button btnFinal  = findViewById(R.id.btn_to_final);
//        btnFinal.setOnClickListener(this);
//
//        Button btnReturning = findViewById(R.id.btn_to_returning);
//        btnReturning.setOnClickListener(this);
//
//        // define before onStart()
//        resultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            Intent intent = result.getData();
//                            useResult(intent);
//                        }
//                    }
//                });
//    }

    @Override
    public void onClick(View button) {
        if (button.getId() == R.id.btn_to_final){
            ((DebuggingActivity) getActivity()).debugging("Click to final");
            ((SingleActivity) getActivity()).navigate(DebuggingActivity.JUMP_TO_FINAL, null);
        }

        if (button.getId() == R.id.btn_to_returning){
            ((DebuggingActivity) getActivity()).debugging("Click to returning");
            ((SingleActivity) getActivity()).navigate(DebuggingActivity.JUMP_TO_RETURNING, null);
        }
    }

//    private void useResult(Intent data) {
//        if (data != null && data.hasExtra(LOGIN)){
//            String txt  = "Welcome, ";
//            txt += (data.getBooleanExtra(GENDER, false)) ? "Mr." : "Mrs.";
//            txt += data.getStringExtra(LOGIN) + "!";
//
//            TextView greeting = (TextView) findViewById(R.id.tv_greeting);
//            greeting.setText(txt);
//        }
//    }
}