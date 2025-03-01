package com.example.application.models.signal;

import com.google.gson.annotations.SerializedName;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("CanBeFinal")
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class DigitalState extends Signal {

    @SerializedName(value = "digital_state")
    private boolean digitalState;

    public DigitalState(Integer id, boolean digitalState) {
        super(id);
        this.digitalState = digitalState;

    }
}