package com.example.application.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.AreasAdapter;
import com.example.application.R;
import com.example.application.entity.Area;
import com.example.application.internet.ConnectionType;
import com.example.application.internet.NetworkUtil;
import com.example.application.internet.ServiceGenerator;
import com.example.application.internet.api.AreaAPI;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView recycler_areas;
    private CoordinatorLayout container;

    private AreaAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = ServiceGenerator.createService(AreaAPI.class);

        recycler_areas = findViewById(R.id.areas_recycler);
        container = findViewById(R.id.main_container);
        recycler_areas.setHasFixedSize(true);
        recycler_areas.setLayoutManager(new LinearLayoutManager(this));

        fetchData();
    }

    private void fetchData() {
        compositeDisposable.add(api.getAll()
                .retry(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayData, throwable -> {
                    Log.i("MAIN", Objects.requireNonNull(throwable.getMessage()));
                    if (NetworkUtil.getConnectivityStatus(this) == ConnectionType.NOT_CONNECTED) {
//                        Snackbar.make(container, "Text label", Snackbar.LENGTH_LONG)
//                                .setAction("Action", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Log.i("MAIN", "Reconnecting");
//                                    }
//                                })
//                                .show();
                    }
                }));
    }

    private void displayData(List<Area> areas) {
        AreasAdapter adapter = new AreasAdapter(this, areas);
        recycler_areas.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
