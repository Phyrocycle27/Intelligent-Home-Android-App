package com.example.application.entity.task.processing;

import com.example.application.exception.InvalidProcessingObjectTypeException;

public enum ProcessingAction {
    SET_PWM_SIGNAL,
    SET_DIGITAL_SIGNAL;

    public static ProcessingAction getProcessingActionByName(String name) {
        try {
            return ProcessingAction.valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidProcessingObjectTypeException();
        }
    }
}
