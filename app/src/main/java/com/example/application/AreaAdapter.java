package com.example.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.entity.Area;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AreaAdapter extends RecyclerView.Adapter<AreaViewHolder> {

    private final Context context;
    private final List<Area> areaList;

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area, parent, false);
        return new AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {
        holder.getName().setText(areaList.get(position).getName());
        holder.getDescription().setText(areaList.get(position).getDescription());

        holder.itemView.setOnClickListener(v -> goToArea(areaList.get(position).getId()));
    }

    private void goToArea(int areaId) {
        // при нажатии на cardView вызывается этот метод и передаётся Id
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }
}
