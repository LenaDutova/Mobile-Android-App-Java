package com.mobile.vedroid.java.storage;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import com.mobile.vedroid.java.MobileApplication;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Device Manager - ⋮ (Additional Actions) - Open In Device Explorer
 * / data / data / your package name / files / datastore
 */
public class DSManager {

    private RxDataStore<Preferences> dataStore = null;

    private static DSManager INSTANCE = null;
    private DSManager() {
        this.dataStore = new RxPreferenceDataStoreBuilder(MobileApplication.mobileApplicationContext, "DS").build();
    }
    public static synchronized DSManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DSManager();
        }
        return INSTANCE;
    }


    public Single<Preferences> saveLightOrNightMode (int value) {
        return dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePreferences = prefs.toMutablePreferences();
            mutablePreferences.set(DataStoreKeys.MODE, value);
            return Single.just(mutablePreferences);
        });
    }

    public Single<Preferences> saveAlwaysRuLanguage (boolean value) {
        return dataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePreferences = prefs.toMutablePreferences();
            mutablePreferences.set(DataStoreKeys.IS_ALWAYS_RU, value);
            return Single.just(mutablePreferences);
        });
    }

    public Flowable<Integer> loadLightOrNightMode() {
        return dataStore.data()
                .map(prefs -> prefs.get(DataStoreKeys.MODE) != null
                        ? prefs.get(DataStoreKeys.MODE)
                        : AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<Boolean> isAlwaysRuLanguage() {
        return dataStore.data()
                .map(prefs -> prefs.get(DataStoreKeys.IS_ALWAYS_RU) != null
                        ? prefs.get(DataStoreKeys.IS_ALWAYS_RU)
                        : false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static class DataStoreKeys{
        public static final Preferences.Key<Boolean> IS_ALWAYS_RU = PreferencesKeys.booleanKey("ru");
        public static final Preferences.Key<Integer> MODE = PreferencesKeys.intKey("mode");
    }
}
