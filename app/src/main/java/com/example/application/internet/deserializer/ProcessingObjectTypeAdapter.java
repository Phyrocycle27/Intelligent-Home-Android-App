package com.example.application.internet.deserializer;

import com.example.application.entity.task.processing.ProcessingAction;
import com.example.application.entity.task.processing.objects.ProcessingObject;
import com.example.application.entity.task.processing.objects.SetDigitalSignalObject;
import com.example.application.entity.task.processing.objects.SetPwmSignalObject;
import com.example.application.exception.UnsupportedProcessingObjectTypeException;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class ProcessingObjectTypeAdapter implements JsonDeserializer<ProcessingObject> {

    @Override
    public ProcessingObject deserialize(JsonElement json, Type typeOfT,
                                        JsonDeserializationContext context) {

        JsonObject jsonObject = json.getAsJsonObject();
        ProcessingAction action = ProcessingAction.getProcessingActionByName(
                jsonObject.get("type").getAsString());

        switch (action) {
            case SET_DIGITAL_SIGNAL:
                return context.deserialize(jsonObject,
                        SetDigitalSignalObject.class);
            case SET_PWM_SIGNAL:
                return context.deserialize(jsonObject,
                        SetPwmSignalObject.class);
            default:
                throw new UnsupportedProcessingObjectTypeException();
        }
    }
}
