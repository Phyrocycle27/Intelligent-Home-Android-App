package com.example.application;

import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.entity.Area;
import com.example.application.internet.ServiceGenerator;
import com.example.application.internet.api.AreaAPI;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import lombok.Getter;

@Getter
public class AreaViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = AreaViewHolder.class.getSimpleName();
    private final AreaAPI api = ServiceGenerator.createService(AreaAPI.class);

    private final TextView name;
    private final TextView description;
    private final ImageButton moreBtn;

    public AreaViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.text_area_name);
        description = itemView.findViewById(R.id.text_area_description);
        moreBtn = itemView.findViewById(R.id.image_btn_more_area);
    }

    public void bind(Area area) {
        name.setText(area.getDescription());
        description.setText(area.getDescription());
        moreBtn.setOnClickListener(this::onMoreBtnClick);
    }

    public void onMoreBtnClick(View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.cardview_actions, popup.getMenu());
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
                Log.d(TAG, "info btn pressed");
                return true;
            case R.id.delete:
                Log.d(TAG, "delete btn pressed");
                createDialog();
                return true;
            case R.id.edit:
                Log.d(TAG, "edit btn pressed");
                return true;
            default:
                return false;
        }
    }

    private void createDialog() {
        new MaterialAlertDialogBuilder(itemView.getContext())
                .setTitle(R.string.dialog_title_remove_confirm)
                .setMessage(R.string.dialog_message_area_remove_confirm)
                .setNeutralButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(R.string.agree, (dialog, which) -> {
                    dialog.dismiss();
                    deleteArea();
                })
                .show();
    }

    private void deleteArea() {
    }

    private void showSuccessfulDeletionSnackbar() {
        Snackbar.make(itemView.getRootView()
                        .findViewById(R.id.coordinator_areas_list),
                R.string.area_successful_deleted,
                Snackbar.LENGTH_SHORT)
                .show();
    }
}
