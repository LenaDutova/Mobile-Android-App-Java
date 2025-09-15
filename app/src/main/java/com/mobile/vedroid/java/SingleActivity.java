package com.mobile.vedroid.java;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mobile.vedroid.java.fragments.FinalFragment;
import com.mobile.vedroid.java.fragments.ReturningFragment;
import com.mobile.vedroid.java.fragments.StartFragment;

public class SingleActivity
        extends DebuggingActivity {

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

    public void navigate (int jump, Bundle args) {
        switch (jump) {
            case DebuggingActivity.JUMP_TO_RETURNING: {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, ReturningFragment.class, args)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case DebuggingActivity.JUMP_FROM_RETURNING: {
                // fixme read bundle in StartFragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, StartFragment.class, args)
                        .commit();

                if (args != null) {
                    getSupportFragmentManager()
                            .popBackStack();
                }
                break;
            }
            case DebuggingActivity.JUMP_TO_FINAL: {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, FinalFragment.class, args)
                        .commit();
                break;
            }
            default: debugging("Navigate Error");
        }
    }
}
