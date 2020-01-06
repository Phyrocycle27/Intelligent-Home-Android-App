package com.example.application.internet;

import com.example.application.entity.Output;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RaspberryAPI {
    @GET("outputs/all")
    Observable<List<Output>> getAll();

    @GET("outputs/all")
    Observable<Output> getAll(@Query("type") String type);

    @GET("outputs/one/{id}")
    Observable<Output> getOne(@Path("id") Integer id);

    @POST("outputs/create")
    Observable<Output> create(@Body Output output);

    @PUT("outputs/one/{id}")
    Observable<Output> update(@Body Output output, @Path("id") Integer id);

    @DELETE("outputs/one/{id}")
    void delete(@Path("id") Integer id);
}
