package com.example.application.models.hardware;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
public class Device {
    private final Long id;
    private final String name;
    private final String description;
    @SerializedName(value = "area_id")
    private final int areaId;
    @SerializedName(value = "signal_inversion")
    private final boolean signalInversion;
    @SerializedName(value = "creation_timestamp")
    private final Date creationTimestamp;
    @SerializedName(value = "update_timestamp")
    private final Date updateTimestamp;
    private final GPIO gpio;
}
