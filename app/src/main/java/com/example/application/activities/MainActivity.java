package com.example.application.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.AreasAdapter;
import com.example.application.R;
import com.example.application.entity.Area;
import com.example.application.internet.ServiceGenerator;
import com.example.application.internet.api.AreaAPI;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView recycler_posts;

    private AreaAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = ServiceGenerator.createService(AreaAPI.class);

        recycler_posts = findViewById(R.id.areas_recycler);
        recycler_posts.setHasFixedSize(true);
        recycler_posts.setLayoutManager(new LinearLayoutManager(this));

        fetchData();
    }

    private void fetchData() {
        compositeDisposable.add(api.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayData));
    }

    private void displayData(List<Area> areas) {
        AreasAdapter adapter = new AreasAdapter(this, areas);
        recycler_posts.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
