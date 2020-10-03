package com.example.application.internet.api;

import com.example.application.entity.Area;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AreaAPI {

    @GET("areas/all")
    Observable<List<Area>> getAll();

    @POST("areas/create")
    Observable<Area> create(@Body Area newArea);
}
