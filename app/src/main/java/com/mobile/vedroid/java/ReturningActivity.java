package com.mobile.vedroid.java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class ReturningActivity extends DebuggingActivity{

    private TextInputEditText login;
    private MaterialButtonToggleGroup toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returning);

        EdgeToEdge.enable(this); // reed https://developer.android.com/develop/ui/views/layout/edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        debugging("HI");

        login = (TextInputEditText) findViewById(R.id.login);
        toggle = (MaterialButtonToggleGroup) findViewById(R.id.toggle_button);

        Button btnStart = (Button) findViewById(R.id.btn_to_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                debugging("Click to final");
                returnToActivity();
            }
        });
    }

    private void returnToActivity(){
        Intent intent = new Intent();

        if (login.getText() != null && !login.getText().toString().isBlank()) {
            intent.putExtra(StartActivity.LOGIN, login.getText().toString());
        } else {
            Snackbar.make(findViewById(R.id.main), "Enter name", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (toggle.getCheckedButtonId() != R.id.btn_not_defined) {
            intent.putExtra(StartActivity.GENDER, (toggle.getCheckedButtonId() == R.id.btn_man));
        } else {
            Snackbar.make(findViewById(R.id.main), "Choose gender", Snackbar.LENGTH_LONG).show();
            return;
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
