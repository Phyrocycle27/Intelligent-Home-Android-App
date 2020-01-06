package com.example.application.recycler_view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.entity.Output;
import com.example.application.recycler_view.view_holder.DeviceViewHolder;

import java.util.List;

public class DevicesListAdapter extends RecyclerView.Adapter<DeviceViewHolder> {
    private List<Output> devices;

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
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void clear() {
        devices.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Output> devices) {
        this.devices.addAll(devices);
        notifyDataSetChanged();
    }
}
