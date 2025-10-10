package com.mobile.vedroid.java.fragments;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public class DebuggingFragment
        extends Fragment {

    private static final boolean DEBUG = true;
    private static final String TAG = "TAG";

    protected ViewBinding binding;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void debugging(String message) {
        if (DEBUG) Log.d(TAG + "_" + this.getClass().getSimpleName(), message);
    }
}
