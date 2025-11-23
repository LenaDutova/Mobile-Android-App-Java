package com.mobile.vedroid.java.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.mobile.vedroid.java.MobileApplication;
import com.mobile.vedroid.java.model.Account;


public class SPManager {

    private SharedPreferences sharedPreferences = null;


    private static SPManager INSTANCE = null;
    private SPManager() {
        this.sharedPreferences =
                MobileApplication.mobileApplicationContext
                .getSharedPreferences("SP", Context.MODE_PRIVATE);
    }
    public static synchronized SPManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new SPManager();
        }
        return INSTANCE;
    }



    public void saveIsAlwaysLanguageRu(boolean mode) {
        if (readIsAlwaysLanguageRu() != mode) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SharedPreferenceKeys.IS_ALWAYS_RU, mode);
            editor.apply();
        }
    }

    public void saveMode(int mode) {
        if (readMode() != mode) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SharedPreferenceKeys.MODE, mode);
            editor.apply();
        }
    }

    public void saveAccount(Account account) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharedPreferenceKeys.ACCOUNT, account.getLogin());
        editor.putBoolean(SharedPreferenceKeys.SEX, account.isGender());
        editor.apply();
    }

    public void saveAccount(String login, boolean gender) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharedPreferenceKeys.ACCOUNT, login);
        editor.putBoolean(SharedPreferenceKeys.SEX, gender);
        editor.apply();
    }

    public void clearAccount() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
        editor.remove(SharedPreferenceKeys.ACCOUNT);
        editor.remove(SharedPreferenceKeys.SEX);
        editor.apply();
    }

    public boolean hasAccount() {
        return sharedPreferences.contains(SharedPreferenceKeys.ACCOUNT);
    }

    public boolean readIsAlwaysLanguageRu (){
        return sharedPreferences.getBoolean(SharedPreferenceKeys.IS_ALWAYS_RU, false);
    }

    public int readMode (){
        return sharedPreferences.getInt(SharedPreferenceKeys.MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public Account readAccount (){
        String login = sharedPreferences.getString(SharedPreferenceKeys.ACCOUNT, "");
        boolean gender = sharedPreferences.getBoolean(SharedPreferenceKeys.SEX, false);
        return new Account(login, gender);
    }

    public static class SharedPreferenceKeys {
        public static final String ACCOUNT = "ACCOUNT";
        public static final String SEX = "SEX";
        public static final String IS_ALWAYS_RU = "ru";
        public static final String MODE = "mode";
    }
}
