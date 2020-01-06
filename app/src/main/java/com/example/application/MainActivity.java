package com.example.application;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.application.internet.RaspberryAPI;
import com.example.application.internet.ServiceGenerator;
import com.example.application.recycler_view.adapter.DevicesListAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Output";
    private SwipeRefreshLayout swipeContainer;
    private Disposable disposable;
    private RecyclerView rv;
    private DevicesListAdapter adapter;
    private RaspberryAPI api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeContainer = findViewById(R.id.swipe_container);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(this::getDeviceList);

        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        api = ServiceGenerator.createService(RaspberryAPI.class);

        getDeviceList();
    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    private void getDeviceList() {
        disposable = api.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(devices -> {
                            Log.i(TAG, "RxJava2: Response from server success");
                            if (adapter != null) {
                                adapter.clear();
                                adapter.addAll(devices);
                                swipeContainer.setRefreshing(false);
                            } else {
                                adapter = new DevicesListAdapter(devices);
                                rv.setAdapter(adapter);
                            }
                        }, t -> {
                            // Вот тут вылазит SocketTimeoutException, и мы должны красиво его перехватить
                            Log.i(TAG, "RxJava2, HTTP Error: " + t.fillInStackTrace());
                        }
                );
    }
}
