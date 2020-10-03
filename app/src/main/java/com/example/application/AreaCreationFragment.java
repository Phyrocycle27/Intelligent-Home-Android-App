package com.example.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.application.entity.Area;
import com.example.application.internet.ServiceGenerator;
import com.example.application.internet.api.AreaAPI;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AreaCreationFragment extends Fragment implements View.OnClickListener {

    private CompositeDisposable compositeDisposable;
    private AreaAPI api;

    private TextInputLayout nameField;
    private TextInputLayout descriptionField;
    @SuppressWarnings("FieldCanBeLocal")
    private ExtendedFloatingActionButton createBtn;

    private Consumer<Area> successSnackbar = areas -> {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity())
                .getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, new AreaListFragment());
        transaction.addToBackStack(null);
        transaction.commit();

        Snackbar.make(Objects.requireNonNull(
                getActivity()).findViewById(R.id.main_coordinator_container),
                R.string.area_created, Snackbar.LENGTH_LONG).show();
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area_creation, container, false);

        initFields(view);

        return view;
    }

    private void initFields(View view) {
        compositeDisposable = new CompositeDisposable();
        api = ServiceGenerator.createService(AreaAPI.class);

        nameField = view.findViewById(R.id.text_input_layout_area_name);
        descriptionField = view.findViewById(R.id.text_input_layout_area_description);

        createBtn = view.findViewById(R.id.fab_extended_area_create);
        createBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = Objects.requireNonNull(nameField.getEditText()).getText().toString().trim();
        String description = Objects.requireNonNull(descriptionField.getEditText()).getText().toString().trim();

        if (name.length() < 3) {
            nameField.setError(getString(R.string.area_name_too_short));
        } else {
            Area area = new Area(0, name, description);

            compositeDisposable.add(api.create(area)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(successSnackbar));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        super.onDestroyView();
    }
}
