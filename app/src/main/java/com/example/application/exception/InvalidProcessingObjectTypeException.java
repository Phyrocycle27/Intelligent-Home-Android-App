package com.example.application.exception;

public class InvalidProcessingObjectTypeException extends RuntimeException {

    public InvalidProcessingObjectTypeException() {
        super("Invalid type of ProcessingObject");
    }
}