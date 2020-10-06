package com.example.application;

import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import lombok.Getter;

@Getter
public class AreaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = AreaViewHolder.class.getSimpleName();

    private final TextView name;
    private final TextView description;
    private final ImageButton moreBtn;

    public AreaViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.text_area_name);
        description = itemView.findViewById(R.id.text_area_description);
        moreBtn = itemView.findViewById(R.id.image_btn_more_area);
        moreBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.cardview_area_actions, popup.getMenu());
        setIcons(popup);
        popup.show();
    }

    private void setIcons(PopupMenu popup) {
        try {
            Field field = popup.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            Object menuPopupHelper = field.get(popup);

            if (menuPopupHelper != null) {
                Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                Method setForceIcons = classPopupHelper.getMethod(
                        "setForceShowIcon", boolean.class);
                setForceIcons.invoke(menuPopupHelper, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
