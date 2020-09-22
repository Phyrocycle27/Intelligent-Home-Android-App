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
public class AreasAdapter extends RecyclerView.Adapter<AreaViewHolder> {

    private Context context;
    private List<Area> areaList;

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_item, parent, false);
        return new AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {
        holder.getName().setText(areaList.get(position).getName());
        holder.getDescription().setText(areaList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }
}
