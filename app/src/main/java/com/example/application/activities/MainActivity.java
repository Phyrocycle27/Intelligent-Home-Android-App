package com.example.application.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.application.R;
import com.example.application.activities.devices.creation.DeviceCreationFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Output";
    private static String DEVICE_CREATION = DeviceCreationFragment.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
