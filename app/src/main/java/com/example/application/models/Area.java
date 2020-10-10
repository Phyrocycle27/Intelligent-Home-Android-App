package com.example.application.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Area {

    private Integer id;
    private String name;
    private String description;
}
