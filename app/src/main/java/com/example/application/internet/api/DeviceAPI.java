package com.example.application.internet.api;

import com.example.application.entity.hardware.Device;
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

public interface DeviceAPI {

    @GET("devices/all")
    Observable<List<Device>> getAll();

    @GET("devices/all")
    Observable<List<Device>> getAllBySignalType(@Query("type") SignalType type);

    @GET("devices/all")
    Observable<List<Device>> getAllByAreaId(@Query("areaId") Integer areaId);

    @GET("devices/all")
    Observable<List<Device>> getAllByAreaIdAndSignalType(@Query("type") SignalType type,
                                                         @Query("areaId") Integer areaId);

    @PUT("devices/one/{id}")
    Observable<Device> update(@Path("id") Integer id, Device newDevice);

    @POST("devices/create")
    Observable<Device> create(@Body Device newDevice);

    @DELETE("devices/one/{id}")
    Observable<ResponseBody> delete(@Path("id") Integer id);


}
