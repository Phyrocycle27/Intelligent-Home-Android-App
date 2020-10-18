package com.example.application;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

public abstract class CardViewHolder<T> extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    private static final String TAG = CardViewHolder.class.getSimpleName();

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(T object);

    @Override
    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.cardview_actions, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
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
                Method setForceIcons = classPopupHelper
                        .getMethod("setForceShowIcon", boolean.class);
                setForceIcons.invoke(menuPopupHelper, true);
            }
        } catch (Exception e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                getItemInfo();
                return true;
            case R.id.delete:
                deleteItem();
                return true;
            case R.id.edit:
                updateItem();
                return true;
            default:
                return false;
        }
    }

    protected abstract void deleteItem();

    protected abstract void updateItem();

    protected abstract void getItemInfo();
}
