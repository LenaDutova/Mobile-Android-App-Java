package com.mobile.vedroid.java.fragments;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.mobile.vedroid.java.BuildConfig;

public class DebuggingFragment
        extends Fragment {

    protected ViewBinding binding;

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    public void debugging(String message) {
        if (BuildConfig.DEBUG) Log.d("TAG_" + this.getClass().getSimpleName(), message);
    }
}
