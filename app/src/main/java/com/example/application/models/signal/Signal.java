package com.example.application.models.signal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
abstract class Signal {

    private final Integer id;
}
