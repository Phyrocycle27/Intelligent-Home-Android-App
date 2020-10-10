package com.example.application.internet.api;

import com.example.application.models.Area;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AreaAPI {

    @GET("areas/all")
    Observable<List<Area>> getAll();

    @POST("areas/create")
    Observable<Area> create(@Body Area newArea);

    @DELETE("areas/one/{id}")
    Observable<Response<Void>> delete(@Path(value = "id") Integer id);
}
