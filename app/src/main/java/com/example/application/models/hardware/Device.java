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

    private Integer id;
    private String name;
    private String description;
    private int areaId;
    private boolean reverse;
    @SerializedName(value = "creation_date")
    private Date creationDate;
    private GPIO gpio;
}
