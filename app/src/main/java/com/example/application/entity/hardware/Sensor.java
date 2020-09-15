package com.example.application.entity.hardware;

import com.example.application.entity.Area;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
public class Sensor {

    private Integer id;
    private String name;
    private Boolean reverse;
    @SerializedName(value = "creation_date")
    private LocalDateTime creationDate;
    private GPIO gpio;
    private Area area;
}
