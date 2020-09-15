package com.example.application.internet.api;

import com.example.application.entity.hardware.AvailableGpioPins;
import com.example.application.entity.signal.SignalType;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GpioHelperAPI {

    @GET("util/gpio/available")
    Observable<AvailableGpioPins> getAvailableGpioPins(@Query("type") SignalType type);
}
