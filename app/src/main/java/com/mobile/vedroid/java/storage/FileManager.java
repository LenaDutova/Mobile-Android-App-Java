package com.mobile.vedroid.java.storage;


import android.content.Context;
import android.util.Log;

import com.mobile.vedroid.java.MobileApplication;
import com.mobile.vedroid.java.model.ApiJoke;
import com.mobile.vedroid.java.model.DenoJoke;
import com.mobile.vedroid.java.model.JokeModelAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Internal storage
 * Device Manager - ⋮ (Additional Actions) - Open In Device Explorer
 * / data / data / your package name / files
 */
public class FileManager {
    private static final String filename = "buckup.txt";

    public boolean checkBuckUpExists (){
        File internalStorageDir = MobileApplication.mobileApplicationContext.getFilesDir();
        File targetFile = new File(internalStorageDir, filename);
        return targetFile.exists();
    }

    public void removeBuckUp (){
        File internalStorageDir = MobileApplication.mobileApplicationContext.getFilesDir();
        internalStorageDir.delete();
    }

    public boolean writeToBuckUp(ArrayList<Serializable> items) throws IOException {
        try (FileOutputStream fos =
                     MobileApplication.mobileApplicationContext
                             .openFileOutput(filename, Context.MODE_APPEND)) {
            for (Serializable item: items) {
                fos.write(item.toString().getBytes());
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

    public String readFromBuckUp() {
//        ArrayList<JokeModelAdapter> items = new ArrayList<>(); // ApiJoke ? DenoJoke
        StringBuilder fileTxtContents = new StringBuilder();
        String line;

        try (FileInputStream fis = MobileApplication.mobileApplicationContext.openFileInput(filename);
             InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(inputStreamReader)){
            while ((line = reader.readLine()) != null){
                fileTxtContents.append(line);
                Log.d("TAG_" + getClass().getSimpleName(), "Read one more joke " + line);
            }
            return fileTxtContents.toString();
        } catch (IOException e){
            Log.e("TAG_" + getClass().getSimpleName(), e.getMessage(), e);
        }
        return null;
    }
}
