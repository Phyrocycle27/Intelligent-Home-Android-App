package com.example.application.models.task.processing.objects;

import com.example.application.models.task.processing.ProcessingAction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public abstract class ProcessingObject {

    private Integer id;
    private ProcessingAction action;
}
