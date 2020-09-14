package com.example.application.entity.signal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class DigitalState extends Signal {

    private boolean digitalState;

    public DigitalState(Integer id, boolean digitalState) {
        super(id);
        this.digitalState = digitalState;

    }
}