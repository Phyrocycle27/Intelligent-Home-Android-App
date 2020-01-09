package com.example.application.internet;

import com.example.application.entity.signal.DigitalState;
import com.example.application.entity.signal.PwmSignal;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface DeviceControlAPI {
    @GET("outputs/control/digital")
    Observable<DigitalState> getDigitalState(@Query("id") Integer id);

    @PUT("outputs/control/digital")
    Observable<DigitalState> setDigitalState(@Body DigitalState state);

    @GET("outputs/control/pwm")
    Observable<PwmSignal> getPwmSignal(@Query("id") Integer id);

    @PUT("outputs/control/pwm")
    Observable<PwmSignal> setPwmSignal(@Body PwmSignal signal);
}
