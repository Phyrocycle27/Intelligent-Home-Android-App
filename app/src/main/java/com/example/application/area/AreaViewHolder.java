package com.example.application.area;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.application.CardViewHolder;
import com.example.application.R;
import com.example.application.models.Area;

public final class AreaViewHolder extends CardViewHolder<Area> {

    private static final String TAG = AreaViewHolder.class.getSimpleName();

    private final AreaAdapter parent;

    private final TextView name;
    private final TextView description;
    private final ImageButton moreBtn;

    public AreaViewHolder(@NonNull View itemView, AreaAdapter parent) {
        super(itemView);

        this.parent = parent;
        name = itemView.findViewById(R.id.text_area_name);
        description = itemView.findViewById(R.id.text_area_description);
        moreBtn = itemView.findViewById(R.id.image_btn_more_area);
    }

    @Override
    public void bind(Area area) {
        name.setText(area.getName());
        description.setText(area.getDescription());
        moreBtn.setOnClickListener(this);
    }

    @Override
    protected void deleteItem() {
        parent.deleteArea(this);
    }

    @Override
    protected void updateItem() {
    }

    @Override
    protected void getItemInfo() {
    }
}
