package com.mobile.vedroid.java;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartActivity
        extends DebuggingActivity
        implements View.OnClickListener {

    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        EdgeToEdge.enable(this); // reed https://developer.android.com/develop/ui/views/layout/edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        debugging("HI");

        Button btnFinal  = findViewById(R.id.btn_to_final);
        btnFinal.setOnClickListener(this);

        Button btnReturning = findViewById(R.id.btn_to_returning);
        btnReturning.setOnClickListener(this);

        // define before onStart()
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            useResult(intent);
                        }
                    }
                });
    }

    @Override
    public void onClick(View button) {
        if (button.getId() == R.id.btn_to_final){
            debugging("Click to final");
            openActivity();
        }

        if (button.getId() == R.id.btn_to_returning){
            debugging("Click to returning");
            openActivityForResult();
        }
    }

    private void openActivity() {
        Intent intent = new Intent(this, FinalActivity.class);
        startActivity(intent);
        finish();
    }

    private void openActivityForResult() {
        Intent intent = new Intent(this, ReturningActivity.class);

//        startActivityForResult(intent, REQUEST);  // @Deprecated way
        resultLauncher.launch(intent);  // New way
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST && resultCode == RESULT_OK){
//            useResult(data);
//        } else {
//            debugging("onActivityResult: request = $REQUEST, result = $resultCode" );
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void useResult(Intent data) {
        if (data != null && data.hasExtra(LOGIN)){
            String txt  = "Welcome, ";
            txt += (data.getBooleanExtra(GENDER, false)) ? "Mr." : "Mrs.";
            txt += data.getStringExtra(LOGIN) + "!";

            TextView greeting = (TextView) findViewById(R.id.tv_greeting);
            greeting.setText(txt);
        }
    }
}