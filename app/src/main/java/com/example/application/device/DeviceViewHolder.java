package com.example.application.device;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.application.CardViewHolder;
import com.example.application.R;
import com.example.application.models.hardware.Device;
import com.google.android.material.switchmaterial.SwitchMaterial;

public final class DeviceViewHolder extends CardViewHolder<Device> {

    private static final String TAG = DeviceViewHolder.class.getSimpleName();

    private final TextView name;
    private final TextView description;
    private final ImageButton moreBtn;
    private final SwitchMaterial switchMaterial;

    private final DeviceAdapter adapter;

    public DeviceViewHolder(@NonNull View itemView, DeviceAdapter adapter) {
        super(itemView);

        this.adapter = adapter;

        name = itemView.findViewById(R.id.text_device_name);
        description = itemView.findViewById(R.id.text_device_description);
        moreBtn = itemView.findViewById(R.id.image_btn_more_device);
        switchMaterial = itemView.findViewById(R.id.switch_control_device_signal);
    }

    @Override
    public void bind(Device device) {
        name.setText(device.getName());
        description.setText(device.getDescription());
        moreBtn.setOnClickListener(this);
    }

    @Override
    protected void deleteItem() {
        Log.d(TAG, "Device delete");
    }

    @Override
    protected void updateItem() {
        Log.d(TAG, "Device update");
    }

    @Override
    protected void getItemInfo() {
        Log.d(TAG, "Device getInfo");
    }
}
