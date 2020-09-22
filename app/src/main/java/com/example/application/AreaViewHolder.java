package com.example.application;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import lombok.Getter;

@Getter
public class AreaViewHolder extends RecyclerView.ViewHolder {

    private final TextView name;
    private final TextView description;

    public AreaViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.area_name);
        description = itemView.findViewById(R.id.area_description);
    }
}
