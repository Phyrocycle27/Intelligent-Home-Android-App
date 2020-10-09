package com.example.application.internet.api;

import com.example.application.models.signal.DigitalState;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SensorSignalAPI {

    @GET("sensors/reading/digital")
    Observable<DigitalState> getDigitalState(@Query("id") Integer id);
}
