package com.example.application.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.application.AreaListFragment;
import com.example.application.R;

public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentById(R.id.main_fragment_container) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_fragment_container, new AreaListFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
