package com.example.application.area;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.application.CreationStatus;
import com.example.application.R;
import com.example.application.internet.ServiceGenerator;
import com.example.application.internet.api.AreaAPI;
import com.example.application.models.Area;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("FieldCanBeLocal")
public class AreaCreationFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = AreaCreationFragment.class.getSimpleName();

    private CompositeDisposable compositeDisposable;
    private AreaAPI api;

    private TextInputLayout nameField;
    private TextInputLayout descriptionField;
    private ExtendedFloatingActionButton createBtn;
    private CoordinatorLayout container;
    private MaterialToolbar toolbar;

    private final Consumer<Area> goToAreasList = areas -> {
        goBack();

        Bundle result = new Bundle();
        result.putString("bundleKey", CreationStatus.SUCCESS.toString());
        getParentFragmentManager().setFragmentResult("requestKey", result);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area_creation, container, false);

        initFields(view);

        return view;
    }

    private void initFields(View view) {
        nameField = view.findViewById(R.id.text_input_layout_area_name);
        descriptionField = view.findViewById(R.id.text_input_layout_area_description);

        createBtn = view.findViewById(R.id.fab_extended_area_create);
        createBtn.setOnClickListener(this);

        container = view.findViewById(R.id.coordinator_area_creation);

        toolbar = requireActivity().findViewById(R.id.toolbar_main);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> goBack());
        toolbar.setTitle(R.string.area_creation);
    }

    @Override
    public void onClick(View view) {
        String name = Objects.requireNonNull(nameField.getEditText()).getText()
                .toString()
                .trim();

        String description = Objects.requireNonNull(descriptionField.getEditText()).getText()
                .toString()
                .trim();

        if (name.length() < 3) {
            nameField.setError(getString(R.string.name_too_short));

        } else {
            Area newArea = new Area(0, name, description);
            hideKeyboard();
            createAreaRequest(newArea);
        }
    }

    private void goBack() {
        hideKeyboard();
        requireActivity().onBackPressed();
    }

    private void hideKeyboard() {
        Activity activity = requireActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void createAreaRequest(Area newArea) {
        compositeDisposable.add(api.create(newArea)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(goToAreasList, throwable -> {
                    Snackbar.make(container, R.string.error_creating_area, Snackbar.LENGTH_LONG)
                            .setAction(R.string.retry, v -> createAreaRequest(newArea))
                            .show();
                    Log.d(TAG, Objects.requireNonNull(throwable.getMessage()));
                }));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        api = ServiceGenerator.createService(AreaAPI.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}