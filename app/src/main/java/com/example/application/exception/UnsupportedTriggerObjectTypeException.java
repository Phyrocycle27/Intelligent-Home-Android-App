package com.example.application.exception;

public class UnsupportedTriggerObjectTypeException extends RuntimeException {

    public UnsupportedTriggerObjectTypeException() {
        super("This type of TriggerObject is currently not supported");
    }
}
