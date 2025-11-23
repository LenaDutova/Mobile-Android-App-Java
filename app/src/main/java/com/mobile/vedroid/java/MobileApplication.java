package com.mobile.vedroid.java;

import android.app.Application;
import android.content.Context;

public class MobileApplication
        extends Application {

    public static Context mobileApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        // Этот вызов отработает раньше, чем загрузятся прочие сущности приложения: сервисы или деятельности
        mobileApplicationContext = getApplicationContext();
    }
}
