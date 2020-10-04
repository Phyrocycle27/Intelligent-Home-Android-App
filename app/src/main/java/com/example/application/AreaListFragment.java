package com.example.application;

import android.content.res.Configuration;
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
import androidx.recyclerview.widget.GridLayoutManager;
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

//TODO: Если список не пустой, то регистрируем данные в Recycler иначе
// выводим кнопку "+ Создать" в центре экрана
@SuppressWarnings("FieldCanBeLocal")
public class AreaListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final String TAG = AreaListFragment.class.getSimpleName();

    private CompositeDisposable compositeDisposable;
    private AreaAPI api;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;

    private AreaAdapter adapter;

    private boolean dataFetchedFlag = false;
    private boolean initUIFlag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_areas_list, container, false);

        initUI(view);

        if (!dataFetchedFlag) {
            fetchData();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        compositeDisposable = new CompositeDisposable();
        api = ServiceGenerator.createService(AreaAPI.class);

        adapter = new AreaAdapter(requireContext());
    }

    private void initUI(View view) {
        Log.d(TAG, "UI init");

        mRecyclerView = view.findViewById(R.id.recycler_areas);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addOnScrollListener(new OnScrollListener());

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_areas_list);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mFloatingActionButton = view.findViewById(R.id.fab_area_create);
        mFloatingActionButton.setOnClickListener(this);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        int orientation = requireActivity().getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(requireContext(), 2);
        } else {
            return new LinearLayoutManager(requireContext());
        }
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                mFloatingActionButton.hide();
            } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                mFloatingActionButton.show();
            }
        }
    }

    @Override
    public void onRefresh() {
        fetchData();
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.setCustomAnimations(
                R.anim.fragment_slide_in,  // enter
                R.anim.fragment_fade_out,  // exit
                R.anim.fragment_fade_in,   // popEnter
                R.anim.fragment_slide_out  // popExit
        );
        transaction.replace(R.id.main_fragment_container, new AreaCreationFragment());
        transaction.commit();
    }

    private void fetchData() {
        Log.d(TAG, "fetch data");
        compositeDisposable.add(api.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayData, throwable ->
                        Log.i(getTag(), Objects.requireNonNull(throwable.getMessage()))
                ));
    }

    private void displayData(List<Area> areas) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        adapter.updateData(areas);
        dataFetchedFlag = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
