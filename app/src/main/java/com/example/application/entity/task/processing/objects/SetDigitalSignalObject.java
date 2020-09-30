package com.example.application.entity.task.processing.objects;

import com.example.application.entity.task.processing.ProcessingAction;

import lombok.Getter;

@Getter
public class SetDigitalSignalObject extends ProcessingObject {

    private Integer deviceId;
    private int delay;
    private boolean targetState;

    public SetDigitalSignalObject(Integer id, ProcessingAction action, Integer deviceId, int delay, boolean targetState) {
        super(id, action);

        this.deviceId = deviceId;
        this.delay = delay;
        this.targetState = targetState;
    }
}
