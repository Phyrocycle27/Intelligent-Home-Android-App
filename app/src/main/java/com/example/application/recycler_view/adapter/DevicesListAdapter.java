package com.example.application.recycler_view.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.entity.Output;
import com.example.application.recycler_view.view_holder.DeviceViewHolder;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DevicesListAdapter extends RecyclerView.Adapter<DeviceViewHolder> {
    private List<Output> devices;
    private long DURATION = 200;
    private boolean on_attach = true;

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
        setAnimation(holder.itemView, position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.d(TAG, "onScrollStateChanged: Called " + newState);
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    // Этот "индусский код" нужно как-то переделать
    private void setAnimation(View itemView, int position) {
        if(!on_attach){
            position = -1;
        }
        boolean isNotFirstItem = position == -1;
        position++;
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(itemView, "alpha", 0.f, 0.5f, 1.0f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animator.setStartDelay(isNotFirstItem ? DURATION / 2 : (position * DURATION / 3));
        animator.setDuration(500);
        animatorSet.play(animator);
        animator.start();
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
