package com.mobile.vedroid.java.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobile.vedroid.java.MobileApplication;
import com.mobile.vedroid.java.model.Account;

/**
 * Device Manager - ⋮ (Additional Actions) - Open In Device Explorer
 * / data / data / your package name / shared_prefs
 */
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
//        editor.remove(SharedPreferenceKeys.ACCOUNT);
//        editor.remove(SharedPreferenceKeys.SEX);
        editor.clear(); // or clear all pairs of key-value
        editor.apply();
    }


    public boolean hasAccount() {
        return sharedPreferences.contains(SharedPreferenceKeys.ACCOUNT);
    }


    public Account readAccount (){
        String login = sharedPreferences.getString(SharedPreferenceKeys.ACCOUNT, "");
        boolean gender = sharedPreferences.getBoolean(SharedPreferenceKeys.SEX, false);
        return new Account(login, gender);
    }

    public static class SharedPreferenceKeys {
        public static final String ACCOUNT = "ACCOUNT";
        public static final String SEX = "SEX";
    }
}
