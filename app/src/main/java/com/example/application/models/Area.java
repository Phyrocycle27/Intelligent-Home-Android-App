package com.example.application.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Area {

    private final Integer id;
    private final String name;
    private final String description;
}
