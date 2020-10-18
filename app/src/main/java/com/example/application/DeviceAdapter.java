package com.example.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.models.hardware.Device;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    private final Context context;
    private final DeviceListFragment parent;
    private final List<Device> deviceList = new ArrayList<>();

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device, parent, false);
        return new DeviceViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        holder.bind(deviceList.get(position));
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public void updateData(List<Device> newList) {
        if (!deviceList.equals(newList)) {
            deviceList.clear();
            deviceList.addAll(newList);
            notifyDataSetChanged();
        }
    }

    public void deleteArea(AreaViewHolder itemView) {
        parent.deleteDevice(deviceList.get(itemView.getLayoutPosition()).getId());
    }
}
