package com.example.application.entity.hardware;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class AvailableGpioPins {

    @SerializedName(value = "available_gpio_pins")
    private Set<Integer> availableGpioPins;
}
