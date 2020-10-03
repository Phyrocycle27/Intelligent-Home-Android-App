package com.example.application;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.application.entity.Area;
import com.example.application.internet.ServiceGenerator;
import com.example.application.internet.api.AreaAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AreaListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private CompositeDisposable compositeDisposable;
    private AreaAPI api;

    @SuppressWarnings("FieldCanBeLocal")
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @SuppressWarnings("FieldCanBeLocal")
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_areas_list, container, false);

        initFields(view);
        fetchData();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initFields(View view) {
        compositeDisposable = new CompositeDisposable();
        api = ServiceGenerator.createService(AreaAPI.class);

        mRecyclerView = view.findViewById(R.id.recycler_areas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_areas_list);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mFloatingActionButton = view.findViewById(R.id.fab_area_create);
        mFloatingActionButton.setOnClickListener(this);
    }

    private void displayData(List<Area> areas) {
        // если список не пустой, то регистрируем данные в Recycler иначе выводим кнопку создать в центре экрана
        AreaAdapter adapter = new AreaAdapter(getActivity(), areas);
        mRecyclerView.setAdapter(adapter);
    }

    private void fetchData() {
        compositeDisposable.add(api.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayData, throwable ->
                        Log.i(getTag(), Objects.requireNonNull(throwable.getMessage()))
                ));
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        fetchData();
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity())
                .getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, new AreaCreationFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
