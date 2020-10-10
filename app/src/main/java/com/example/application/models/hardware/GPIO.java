package com.example.application.models.hardware;

import com.example.application.models.signal.SignalType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = {"gpio"})
@AllArgsConstructor
public class GPIO {

    private Integer gpio;
    private SignalType type;
    private GPIOMode mode;
}