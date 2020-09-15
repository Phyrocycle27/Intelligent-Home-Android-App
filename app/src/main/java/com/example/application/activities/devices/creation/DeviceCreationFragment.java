package com.example.application.activities.devices.creation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.application.R;
import com.google.android.material.textfield.TextInputEditText;

public class DeviceCreationFragment extends Fragment {

    private TextInputEditText name;
    private TextInputEditText signalType;
    private TextInputEditText GPIO;
    private TextInputEditText InversionOfSignal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.device_creation_fragment, container, false);
    }
}
