package com.example.application.entity.signal;



public class PwmSignal extends Signal {

    private Integer pwmSignal;

    public PwmSignal(Integer outputId, Integer pwmSignal) {
        super(outputId);
        this.pwmSignal = pwmSignal;
    }

    public Integer getPwmSignal() {
        return pwmSignal;
    }
}
