package com.example.application.recycler_view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.entity.Output;
import com.example.application.entity.signal.DigitalState;
import com.example.application.internet.DeviceControlAPI;
import com.example.application.internet.ServiceGenerator;
import com.example.application.recycler_view.view_holder.DeviceViewHolder;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DevicesListAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private String TAG = "ANIM";

    private List<Output> devices;
    private DeviceControlAPI api;
    private Disposable disposable;

    public DevicesListAdapter(List<Output> devices) {
        this.devices = devices;
        api = ServiceGenerator.createService(DeviceControlAPI.class);
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_item, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        holder.getNameView().setText(devices.get(position).getName());

        disposable = api.getDigitalState(devices.get(position).getOutputId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> {
                            holder.getSwitchCompat().setChecked(state.getDigitalState());
                            Log.d(TAG, "onBindViewHolder: Success got state");
                        },
                        throwable -> Log.d(TAG, "onBindViewHolder: RxJava response from server error"));

        holder.getSwitchCompat().setOnCheckedChangeListener((buttonView, isChecked) ->
                disposable = api.setDigitalState(
                        new DigitalState(devices.get(position).getOutputId(), isChecked))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(state -> {
                                    buttonView.setChecked(state.getDigitalState());
                                    Log.d(TAG, "onBindViewHolder: Success setup state");
                                },
                                throwable -> Log.d(TAG, "onBindViewHolder: RxJava response from server error")));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        runAnimation(recyclerView);
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void runAnimation(RecyclerView recyclerView) {
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void clear() {
        if (!devices.isEmpty()) {
            devices.clear();
            notifyDataSetChanged();
        }
    }

    public void addAll(List<Output> devices, RecyclerView rv) {
        this.devices.addAll(devices);
        runAnimation(rv);
    }

    public void destroyDisposable() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
