package com.example.application.models.task;

import com.example.application.models.task.processing.objects.ProcessingObject;
import com.example.application.models.task.trigger.objects.TriggerObject;

import java.util.HashSet;
import java.util.Set;

public class Task {

    private Integer id;
    private String name;
    private Set<TriggerObject> triggerObjects = new HashSet<>();
    private Set<ProcessingObject> processingObjects = new HashSet<>();
}
