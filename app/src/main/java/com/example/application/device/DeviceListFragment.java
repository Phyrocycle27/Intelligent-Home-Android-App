package com.example.application.device;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.application.CreationStatus;
import com.example.application.R;
import com.example.application.internet.ServiceGenerator;
import com.example.application.internet.api.DeviceAPI;
import com.example.application.models.hardware.Device;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("FieldCanBeLocal")
public class DeviceListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final String TAG = DeviceListFragment.class.getSimpleName();

    private CompositeDisposable compositeDisposable;
    private DeviceAPI api;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;
    private CoordinatorLayout container;
    private MaterialToolbar toolbar;

    private final String KEY_RECYCLER_STATE = "RECYCLER_OFFSET";
    private static Bundle mBundleRecyclerViewState;

    private DeviceAdapter adapter;

    private Long areaId = -1L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);

        Bundle args = getArguments();
        if (areaId == -1 && args != null) {
            areaId = args.getLong("AREA_ID");
        }

        initUI(view);
        fetchData();

        return view;
    }

    private void initUI(View view) {
        Log.d(TAG, "UI init");

        initRecyclerView(view);
        initSwipeRefreshLayout(view);
        initFAB(view);
        initToolbar();

        container = view.findViewById(R.id.coordinator_devices_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.setAdapter(adapter);

        if (mBundleRecyclerViewState != null) {
            int offset = mBundleRecyclerViewState.getInt(KEY_RECYCLER_STATE);
            mRecyclerView.scrollBy(0, offset);
            Log.d(TAG, "OFFSET is " + offset);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        compositeDisposable = new CompositeDisposable();
        api = ServiceGenerator.createService(DeviceAPI.class);

        adapter = new DeviceAdapter(requireContext(), this);

        getParentFragmentManager().setFragmentResultListener("requestKey", this, (key, bundle) -> {
            CreationStatus status = CreationStatus.valueOf(bundle.getString("bundleKey"));
            //noinspection SwitchStatementWithTooFewBranches
            switch (status) {
                case SUCCESS:
                    Snackbar.make(container, R.string.device_successful_created, Snackbar.LENGTH_SHORT)
                            .show();
                    fetchData();
                    break;
            }
        });
    }

    private void initRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_devices);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManagerForRV());
        mRecyclerView.addOnScrollListener(new DeviceListFragment.OnScrollRecyclerViewListener());
    }

    private void initSwipeRefreshLayout(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_list_devices);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initFAB(View view) {
        mFloatingActionButton = view.findViewById(R.id.fab_create_device);
        mFloatingActionButton.setOnClickListener(this);
    }

    private void initToolbar() {
        toolbar = requireActivity().findViewById(R.id.toolbar_main);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        toolbar.setTitle(R.string.devices);
    }

    private RecyclerView.LayoutManager getLayoutManagerForRV() {
        int orientation = requireActivity().getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(requireContext(), 2);
        } else {
            return new LinearLayoutManager(requireContext());
        }
    }

    private class OnScrollRecyclerViewListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                mFloatingActionButton.hide();
            } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                mFloatingActionButton.show();
            }
        }
    }

    @Override
    public void onRefresh() {
        fetchData();
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
        );

        Bundle args = new Bundle();
        DeviceCreationFragment newFrag = new DeviceCreationFragment();
        args.putLong("AREA_ID", areaId);
        newFrag.setArguments(args);

        transaction.replace(R.id.main_fragment_container, newFrag);
        transaction.commit();
    }

    private void fetchData() {
        Log.d(TAG, "Data fetch");
        compositeDisposable.add(api.getAllByAreaId(areaId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayData, throwable -> {
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    Snackbar.make(container, R.string.error_downloading_data,
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.retry, v -> fetchData())
                                    .show();

                            Log.d(TAG, Objects.requireNonNull(throwable.getMessage()));
                        }
                ));
    }

    private void displayData(List<Device> devices) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        adapter.updateData(devices);
    }

    /*
     ****************** DEVICE DELETING ************************
     */
    public void deleteDevice(Long id) {
        showConfirmDeleteDeviceDialog(id);
    }

    private void showConfirmDeleteDeviceDialog(Long deviceId) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.dialog_title_remove_confirm)
                .setMessage(R.string.dialog_message_device_remove_confirm)
                .setNeutralButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(R.string.agree, (dialog, which) -> {
                    doDeleteDeviceRequest(deviceId);
                    dialog.dismiss();
                })
                .show();
    }

    private void doDeleteDeviceRequest(Long deviceId) {
        compositeDisposable.add(api.delete(deviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(voidResponse -> {
                            Snackbar.make(container, R.string.device_successful_deleted,
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                            fetchData();
                        }, throwable -> {
                            Snackbar.make(container, R.string.device_error_deleted,
                                    Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.retry, v -> doDeleteDeviceRequest(deviceId))
                                    .show();
                            Log.d(TAG, Objects.requireNonNull(throwable.getMessage()));
                        }
                ));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        mBundleRecyclerViewState = new Bundle();
        int offset = mRecyclerView.computeVerticalScrollOffset();
        mBundleRecyclerViewState.putInt(KEY_RECYCLER_STATE, offset);
        Log.d(TAG, "Offset " + offset);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
