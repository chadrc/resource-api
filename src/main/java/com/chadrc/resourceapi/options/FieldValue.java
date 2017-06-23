package com.chadrc.resourceapi.options;

public class FieldValue {
    private String field = null;
    private Object value = null;

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return field + " -> " + value;
    }
}
