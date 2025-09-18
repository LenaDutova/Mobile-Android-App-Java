package com.mobile.vedroid.java;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.mobile.vedroid.java.fragments.FinalFragment;
import com.mobile.vedroid.java.fragments.ReturningFragment;
import com.mobile.vedroid.java.fragments.StartFragment;

public class SingleActivity
        extends AppCompatActivity {

    private static final boolean DEBUG = true;
    private static final String TAG = "TAG";

    public static final String LOGIN = "LOGIN";
    public static final String GENDER = "GENDER";

    public static final int JUMP_TO_RETURNING = 1;
    public static final int JUMP_FROM_RETURNING = 2;
    public static final int JUMP_TO_FINAL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        EdgeToEdge.enable(this); // reed https://developer.android.com/develop/ui/views/layout/edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        debugging("HI");

//        // or use android:name in FragmentContainerView
//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.main, StartFragment.class, savedInstanceState)
//                    .commit();
//        }
    }

    private void debugging(String message) {
        if (DEBUG) Log.d(TAG + "_" + this.getLocalClassName(), message);
    }

    public void showSnackBar (String message){
        Snackbar.make(findViewById(R.id.main), message, Snackbar.LENGTH_LONG).show();
    }

    public void showToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void navigate (int jump, Bundle args) {
        switch (jump) {
            case JUMP_TO_RETURNING: {
                debugging("Open fragment to registration");
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.main, ReturningFragment.class, null)
                        .addToBackStack("start")    // Can return to StartFragment
                        .commit();
                break;
            }
            case JUMP_FROM_RETURNING: {
                debugging("Return to start fragment with registration data");
                if (args != null) getSupportFragmentManager().popBackStack();   // Not return to StartFragment

//                StartFragment fragment = new StartFragment();
//                fragment.setArguments(args);
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .setReorderingAllowed(true)
//                        .replace(R.id.main, fragment) // fragment with nested arguments
//                        .commit();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, StartFragment.class, args)  // fragment and arguments
                        .setReorderingAllowed(true)
                        .commit();

                break;
            }
            case JUMP_TO_FINAL: {
                debugging("Open fragment with recycle view list");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, FinalFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            }
            default: debugging("Navigate Error");
        }
    }
}
