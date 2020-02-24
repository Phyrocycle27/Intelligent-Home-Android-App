package com.example.application.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.application.R;
import com.example.application.activities.devices.creation.DeviceCreationFragment;
import com.example.application.activities.devices.list.DevicesListFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Output";
    private static String DEVICE_CREATION = DeviceCreationFragment.class.getName();
    private static String DEVICE_LIST = DevicesListFragment.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();

            Fragment deviceCreationFrag = manager.findFragmentByTag(DEVICE_CREATION);

            if (deviceCreationFrag == null) {
                Log.d(TAG, "Добавление фрагмента");

                ft.add(R.id.fragment_container, new DeviceCreationFragment(), DEVICE_CREATION)
                        .hide(Objects.requireNonNull(manager.findFragmentById(R.id.devices_list_fragment)))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(DEVICE_CREATION)
                        .commit();

                setToolbarTitle("Создание устройства");
            } else {
                Log.d(TAG, "Удаление фрагмента");
                goToBack();
            }
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            goToBack();
        }
    }

    private void goToBack() {
        getSupportFragmentManager().popBackStack();

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        Log.d(TAG, "f instance is " + (f instanceof DeviceCreationFragment) + " f visible is " + Objects.requireNonNull(f).isVisible());
        if (f instanceof DevicesListFragment && f.isVisible()) {
            setToolbarTitle("Список устройств");
        }
    }

    public void setToolbarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }
}
