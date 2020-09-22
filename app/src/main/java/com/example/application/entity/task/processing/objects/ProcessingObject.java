package com.example.application.entity.task.processing.objects;

import com.example.application.entity.task.processing.ProcessingAction;

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
