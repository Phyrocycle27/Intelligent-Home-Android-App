package com.example.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.models.Area;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AreaAdapter extends RecyclerView.Adapter<AreaViewHolder> {

    private final Context context;
    private final AreaListFragment parent;
    private final List<Area> areaList = new ArrayList<>();

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area, parent, false);
        return new AreaViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {
        holder.bind(areaList.get(position));
        holder.itemView.setOnClickListener(v -> goToArea(areaList.get(position).getId()));
    }

    private void goToArea(int areaId) {
        parent.goToArea(areaId);
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    public void updateData(List<Area> newList) {
        if (!areaList.equals(newList)) {
            areaList.clear();
            areaList.addAll(newList);
            notifyDataSetChanged();
        }
    }

    public void deleteArea(AreaViewHolder itemView) {
        parent.deleteArea(areaList.get(itemView.getLayoutPosition()).getId());
    }
}
