package com.mobile.vedroid.java.storage;


import android.content.Context;
import android.util.Log;

import com.mobile.vedroid.java.MobileApplication;
import com.mobile.vedroid.java.model.JokeAdapterModel;
import com.mobile.vedroid.java.model.JokeFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

/**
 * Internal storage
 * Device Manager - ⋮ (Additional Actions) - Open In Device Explorer
 * / data / data / your package name / files
 */
public class FileManager
        implements OfflineStorage {

    private static final String filename = "buckup.txt";


    @Override
    public Flowable<List<JokeAdapterModel>> load() {
        if (!checkBuckUpExists()) return Flowable.just(Collections.emptyList());
        return readFromBuckUp();
    }

    @Override
    public Completable save(List<JokeAdapterModel> items) {
        return Completable.fromAction(() -> writeToBuckUp(items));
    }

    // region // File utility: IO in Internal storage

    protected boolean checkBuckUpExists (){
        File internalStorageDir = MobileApplication.mobileApplicationContext.getFilesDir();
        File targetFile = new File(internalStorageDir, filename);
        return targetFile.exists();
    }

    protected void removeBuckUp (){
        File internalStorageDir = MobileApplication.mobileApplicationContext.getFilesDir();
        internalStorageDir.delete();
    }

    protected void writeToBuckUp(List<JokeAdapterModel> items) {
        try (FileOutputStream fos =
                     MobileApplication.mobileApplicationContext
                             .openFileOutput(filename, Context.MODE_APPEND)) {
            for (JokeAdapterModel item: items) {
                fos.write(JokeFactory.valueOf(item).getBytes());
            }
        } catch (FileNotFoundException e){
            // lack of permission?
            Log.e("TAG_" + getClass().getSimpleName(), e.getMessage(), e);
        } catch (IOException e) {
            Log.e("TAG_" + getClass().getSimpleName(), e.getMessage(), e);
        }
    }

    protected Flowable<List<JokeAdapterModel>> readFromBuckUp() {
        List<JokeAdapterModel> items = new ArrayList<>(); // ApiJoke ? DenoJoke
        String line;

        try (FileInputStream fis = MobileApplication.mobileApplicationContext.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader)){
            while ((line = reader.readLine()) != null){
                if (!JokeFactory.parseToJokeAttr(line)){
                    items.add(JokeFactory.create());
                }
            }
            return Flowable.just(items);
        } catch (IOException e){
            Log.e("TAG_" + getClass().getSimpleName(), e.getMessage(), e);
        }
        return Flowable.just(Collections.emptyList());
    }

    protected String readStringFromBuckUp() {
        StringBuilder fileTxtContents = new StringBuilder();
        String line;

        try (FileInputStream fis = MobileApplication.mobileApplicationContext.openFileInput(filename);
             InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(inputStreamReader)){
            while ((line = reader.readLine()) != null){
                fileTxtContents.append(line);
                Log.d("TAG_" + getClass().getSimpleName(), "Read one more line: " + line);
            }
            return fileTxtContents.toString();
        } catch (IOException e){
            Log.e("TAG_" + getClass().getSimpleName(), e.getMessage(), e);
        }
        return null;
    }

    // endregion
}
