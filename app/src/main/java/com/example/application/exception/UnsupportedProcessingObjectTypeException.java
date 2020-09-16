package com.example.application.exception;

public class UnsupportedProcessingObjectTypeException extends RuntimeException {

    public UnsupportedProcessingObjectTypeException() {
        super("This type of ProcessingObject is currently not supported");
    }
}
