package com.example.application.recycler_view.view_holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    private TextView nameView;

    public DeviceViewHolder(View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.device_name);
    }

    public TextView getNameView() {
        return nameView;
    }
}
