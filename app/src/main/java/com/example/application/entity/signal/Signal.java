package com.example.application.entity.signal;

abstract class Signal {

    private Integer outputId;

    Signal(Integer outputId) {
        this.outputId = outputId;
    }

    public Integer getOutputId() {
        return outputId;
    }
}
