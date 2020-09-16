package com.example.application.entity.task.trigger;

import com.example.application.exception.InvalidTriggerObjectTypeException;

public enum TriggerAction {
    CHANGE_DIGITAL_SIGNAL;

    public static TriggerAction getTriggerActionByName(String name) {
        try {
            return TriggerAction.valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidTriggerObjectTypeException();
        }
    }
}
