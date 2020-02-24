package com.example.application.activities.devices.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.application.R;
import com.example.application.internet.RaspberryAPI;
import com.example.application.internet.ServiceGenerator;
import com.example.application.recycler_view.adapter.DevicesListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DevicesListFragment extends Fragment {

    private static final String TAG = "Output";

    // Views
    private RecyclerView rv;
    private SwipeRefreshLayout swipeContainer;
    private CoordinatorLayout coordinatorLayout;
    // Adapters
    private DevicesListAdapter adapter;
    // RxJava
    private Disposable disposable;
    // API
    private RaspberryAPI api;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: list");
        View view = inflater.inflate(R.layout.device_list_fragment, container, false);

        coordinatorLayout = view.findViewById(R.id.device_list_coordinator);

        swipeContainer = view.findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(() -> {
            adapter.clear();
            getDeviceList();
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        rv = view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        api = ServiceGenerator.createService(RaspberryAPI.class);

        getDeviceList();

        return view;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: list");
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (adapter != null) {
            adapter.destroyDisposable();
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
