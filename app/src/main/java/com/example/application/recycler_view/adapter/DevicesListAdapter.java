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
import com.example.application.recycler_view.view_holder.DeviceViewHolder;

import java.util.List;

public class DevicesListAdapter extends RecyclerView.Adapter<DeviceViewHolder> {
    private List<Output> devices;
    private String TAG = "ANIM";

    public DevicesListAdapter(List<Output> devices) {
        this.devices = devices;
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
        Log.d(TAG, "onBindViewHolder: ");
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        Log.d(TAG, "onAttachedToRecyclerView: ");
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
}
