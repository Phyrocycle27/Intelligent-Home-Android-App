package com.example.application.internet.api;

import com.example.application.entity.Area;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AreaAPI {

    @GET("devices/all")
    Observable<List<Area>> getAll();
}
