package com.example.application.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.AreaListFragment;
import com.example.application.R;

public class MainActivity extends AppCompatActivity {

    private AreaListFragment areaListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_container, new AreaListFragment()).addToBackStack(null)
                .commit();

        Log.d("MAIN", getSupportFragmentManager().getFragments().toString());
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        Log.d("MAIN", "Curr is " + count);
        if (count == 0) {
            super.onBackPressed();
//            additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
