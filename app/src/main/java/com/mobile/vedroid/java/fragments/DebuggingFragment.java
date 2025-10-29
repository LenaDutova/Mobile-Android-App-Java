package com.mobile.vedroid.java.fragments;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.mobile.vedroid.java.BuildConfig;

public class DebuggingFragment
        extends Fragment {

    private static final String TAG = "TAG";

    protected ViewBinding binding;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void debugging(String message) {
        if (BuildConfig.DEBUG) Log.d(TAG + "_" + this.getClass().getSimpleName(), message);
    }
}
