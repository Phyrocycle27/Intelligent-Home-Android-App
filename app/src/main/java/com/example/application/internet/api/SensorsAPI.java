package com.example.application.internet.api;

import com.example.application.entity.hardware.Sensor;
import com.example.application.entity.signal.SignalType;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SensorsAPI {

    @GET("sensors/all")
    Observable<List<Sensor>> getAll();

    @GET("sensors/all")
    Observable<List<Sensor>> getAllBySignalType(@Query("type") SignalType type);

    @GET("sensors/all")
    Observable<List<Sensor>> getAllByAreaId(@Query("areaId") Integer areaId);

    @GET("sensors/all")
    Observable<List<Sensor>> getAllByAreaIdAndSignalType(@Query("type") SignalType type,
                                                         @Query("areaId") Integer areaId);

    @PUT("sensors/one/{id}")
    Observable<Sensor> update(@Path("id") Integer id, Sensor newSensor);

    @POST("sensors/create")
    Observable<Sensor> create(@Body Sensor newSensor);

    @DELETE("sensors/one/{id}")
    Observable<ResponseBody> delete(@Path("id") Integer id);
}
