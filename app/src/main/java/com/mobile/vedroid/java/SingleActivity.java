package com.mobile.vedroid.java;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
public class SingleActivity
        extends AppCompatActivity {

    private static final boolean DEBUG = true;
    private static final String TAG = "TAG";

    public static final String LOGIN = "LOGIN";
    public static final String GENDER = "GENDER";
    public static final String REGISTER = "REGISTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        EdgeToEdge.enable(this); // reed https://developer.android.com/develop/ui/views/layout/edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        debugging("HI");

//        // or use android:name in FragmentContainerView
//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.main, StartFragment.class, savedInstanceState)
//                    .commit();
//        }
    }

    private void debugging(String message) {
        if (DEBUG) Log.d(TAG + "_" + this.getLocalClassName(), message);
    }

    public void showSnackBar (String message){
        Snackbar.make(findViewById(R.id.nav_host_fragment), message, Snackbar.LENGTH_LONG).show();
    }

    public void showToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
