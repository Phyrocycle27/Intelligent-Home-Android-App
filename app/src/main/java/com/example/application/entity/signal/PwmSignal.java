package com.example.application.entity.signal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class PwmSignal extends Signal {

    private int pwmSignal;

    public PwmSignal(Integer id, int pwmSignal) {
        super(id);
        this.pwmSignal = pwmSignal;
    }
}
