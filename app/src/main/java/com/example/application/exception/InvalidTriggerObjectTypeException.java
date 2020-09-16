package com.example.application.exception;

public class InvalidTriggerObjectTypeException extends RuntimeException {

    public InvalidTriggerObjectTypeException() {
        super("Invalid type of ProcessingObject");
    }
}
