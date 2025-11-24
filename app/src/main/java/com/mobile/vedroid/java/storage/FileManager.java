package com.mobile.vedroid.java.storage;


import android.content.Context;
import android.util.Log;

import com.mobile.vedroid.java.MobileApplication;
import com.mobile.vedroid.java.model.Joke;
import com.mobile.vedroid.java.model.JokeAdapterModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Internal storage
 * Device Manager - ⋮ (Additional Actions) - Open In Device Explorer
 * / data / data / your package name / files
 */
public class FileManager
        implements OfflineStorage {

    private static final String filename = "buckup.txt";

    @Override
    public boolean isExists() {
        return checkBuckUpExists();
    }

    @Override
    public List<JokeAdapterModel> load() {
        return readFromBuckUp();
    }

    @Override
    public void save(List<JokeAdapterModel> items) {
        writeToBuckUp(items);
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

    protected boolean writeToBuckUp(List<JokeAdapterModel> items) {
        try (FileOutputStream fos =
                     MobileApplication.mobileApplicationContext
                             .openFileOutput(filename, Context.MODE_APPEND)) {
            for (JokeAdapterModel item: items) {
                fos.write(Joke.JokeFactory.valueOf(item).getBytes());
            }
            return true;
        } catch (FileNotFoundException e){
            // lack of permission?
            Log.e("TAG_" + getClass().getSimpleName(), e.getMessage(), e);
        } catch (IOException e) {
            Log.e("TAG_" + getClass().getSimpleName(), e.getMessage(), e);
        }
        return false;
    }

    protected List<JokeAdapterModel> readFromBuckUp() {
        List<JokeAdapterModel> items = new ArrayList<>(); // ApiJoke ? DenoJoke
        String line;

        try (FileInputStream fis = MobileApplication.mobileApplicationContext.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader)){
            while ((line = reader.readLine()) != null){
                if (!Joke.JokeFactory.parseToJokeAttr(line)){
                    items.add(Joke.JokeFactory.create());
                }
            }
            return items;
        } catch (IOException e){
            Log.e("TAG_" + getClass().getSimpleName(), e.getMessage(), e);
        }
        return null;
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
