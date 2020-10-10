package com.example.application.internet.deserializer;

import com.example.application.exception.UnsupportedTriggerObjectTypeException;
import com.example.application.models.task.trigger.TriggerAction;
import com.example.application.models.task.trigger.objects.ChangeDigitalSignalObject;
import com.example.application.models.task.trigger.objects.TriggerObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class TriggerObjectTypeAdapter implements JsonDeserializer<TriggerObject> {

    @Override
    public TriggerObject deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) {

        JsonObject jsonObject = json.getAsJsonObject();
        TriggerAction action = TriggerAction.getTriggerActionByName(
                jsonObject.get("type").getAsString());

        switch (action) {
            case CHANGE_DIGITAL_SIGNAL:
                return context.deserialize(jsonObject,
                        ChangeDigitalSignalObject.class);
            default:
                throw new UnsupportedTriggerObjectTypeException();
        }
    }
}
