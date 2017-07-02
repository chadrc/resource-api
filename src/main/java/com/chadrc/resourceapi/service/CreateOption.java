package com.chadrc.resourceapi.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CreateOption {

    private List<SchemaType> schemaTypes = new ArrayList<>();

    CreateOption(Constructor constructor) {
        Type[] params = constructor.getParameterTypes();
        for (Type type : params) {
            schemaTypes.add(new SchemaType((Class) type));
        }
    }

    public List<SchemaType> getSchemaTypes() {
        return schemaTypes;
    }
}
