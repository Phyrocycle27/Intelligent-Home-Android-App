package com.example.application.models.hardware;

import com.example.application.models.signal.SignalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GPIO {

    private final Integer gpioPin;
    private final SignalType signalType;
    private final GPIOMode pinMode;
}