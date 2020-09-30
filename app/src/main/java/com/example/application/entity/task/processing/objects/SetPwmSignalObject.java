package com.example.application.entity.task.processing.objects;

import com.example.application.entity.task.processing.ProcessingAction;

import lombok.Getter;

@Getter
public class SetPwmSignalObject extends ProcessingObject {

    private Integer deviceId;
    private int delay;
    private int targetSignal;

    public SetPwmSignalObject(Integer id, ProcessingAction action, Integer deviceId, int delay, int targetSignal) {
        super(id, action);

        this.deviceId = deviceId;
        this.delay = delay;
        this.targetSignal = targetSignal;
    }
}
