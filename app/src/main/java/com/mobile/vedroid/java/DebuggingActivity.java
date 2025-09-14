package com.mobile.vedroid.java;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class DebuggingActivity extends AppCompatActivity {

    private static final boolean DEBUG = true;
    private static final String TAG = "TAG";

    protected static final int REQUEST = 100;
    protected static final String LOGIN = "LOGIN";
    protected static final String GENDER = "GENDER";

    protected void debugging(String message) {
        if (DEBUG) Log.d("TAG_" + this.getLocalClassName(), message);
    }
}
