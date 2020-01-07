package com.example.application;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.application.internet.RaspberryAPI;
import com.example.application.internet.ServiceGenerator;
import com.example.application.recycler_view.SwipeController;
import com.example.application.recycler_view.adapter.DevicesListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Output";
    // Views
    private SwipeRefreshLayout swipeContainer;
    private FloatingActionButton fab;
    private RecyclerView rv;
    private CoordinatorLayout coordinatorLayout;
    // Adapters
    private DevicesListAdapter adapter;
    // RxJava
    private Disposable disposable;
    // API
    private RaspberryAPI api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = findViewById(R.id.coordinator);

        swipeContainer = findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(() -> {
            adapter.clear();
            getDeviceList();
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        fab = findViewById(R.id.floating_action_button);

        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(new SwipeController());
        itemTouchhelper.attachToRecyclerView(rv);

        //  Setup Raspberry Pi API
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
                                adapter.addAll(devices, rv);
                                swipeContainer.setRefreshing(false);
                            } else {
                                adapter = new DevicesListAdapter(devices);
                                rv.setAdapter(adapter);
                            }
                        }, t -> {
                            if (t instanceof SocketTimeoutException ||
                                    t instanceof ConnectException) {
                                Snackbar.make(coordinatorLayout, "Ошибка подключения",
                                        Snackbar.LENGTH_INDEFINITE)
                                        .setAction("ПОВТОРИТЬ", view -> getDeviceList())
                                        .show();
                            }
                            // Вот тут вылазит SocketTimeoutException, и мы должны красиво его перехватить
                            Log.i(TAG, "RxJava2, HTTP Error: " + t.fillInStackTrace());
                        }
                );
    }
}
