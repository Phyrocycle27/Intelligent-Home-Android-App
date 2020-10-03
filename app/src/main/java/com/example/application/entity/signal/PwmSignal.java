package com.example.application.entity.signal;

import com.google.gson.annotations.SerializedName;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("CanBeFinal")
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class PwmSignal extends Signal {

    @SerializedName(value = "pwm_signal")
    private int pwmSignal;

    public PwmSignal(Integer id, int pwmSignal) {
        super(id);
        this.pwmSignal = pwmSignal;
    }
}
