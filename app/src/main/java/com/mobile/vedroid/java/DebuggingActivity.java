package com.mobile.vedroid.java;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class DebuggingActivity
        extends AppCompatActivity {

    private static final boolean DEBUG = true;
    private static final String TAG = "TAG";

    public static final String LOGIN = "LOGIN";
    public static final String GENDER = "GENDER";

    public static final int JUMP_TO_RETURNING = 1;
    public static final int JUMP_FROM_RETURNING = 2;
    public static final int JUMP_TO_FINAL = 3;

    public void debugging(String message) {
        if (DEBUG) Log.d(TAG + "_" + this.getLocalClassName(), message);
    }
}
