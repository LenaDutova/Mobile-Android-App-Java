package com.mobile.vedroid.java.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.mobile.vedroid.java.BuildConfig;
import com.mobile.vedroid.java.databinding.ActivityBinding;

import java.util.Locale;

public class SingleActivity
        extends AppCompatActivity {

    private ActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // reed https://developer.android.com/develop/ui/views/layout/edge-to-edge

        binding = ActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void setLocaleAlwaysRu(boolean always) {
        boolean needUpdateUI = true;
        Locale locale = null;

        if (always){
            needUpdateUI = !getResources().getConfiguration().getLocales().get(0).getLanguage().equals("ru");
            locale = new Locale("ru");
        } else {
            needUpdateUI = !getResources().getConfiguration().getLocales().get(0).getLanguage()
                    .equals(Locale.getDefault().getLanguage());
            locale = new Locale(Locale.getDefault().getLanguage());
        }
        debugging("Set Locale (" + locale.getLanguage() + "), need update UI (" + needUpdateUI + ")");

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        if (needUpdateUI) recreate();
    }


    public void showSnackBar (String message){
        Snackbar.make(binding.main, message, Snackbar.LENGTH_LONG).show();
    }

    public void showToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    private void debugging(String message) {
        if (BuildConfig.DEBUG) Log.d("TAG_" + this.getClass().getSimpleName(), message);
    }
}
