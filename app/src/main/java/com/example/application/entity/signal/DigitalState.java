package com.example.application.entity.signal;

public class DigitalState extends Signal {

    private Boolean digitalState;

    public DigitalState(Integer outputId, Boolean digitalState) {
        super(outputId);
        this.digitalState = digitalState;
    }

    public Boolean getDigitalState() {
        return digitalState;
    }
}