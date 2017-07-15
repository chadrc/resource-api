package com.chadrc.resourceapi.basic.crud.read;

public class Sort {
    private String field;
    private SortDirection direction = SortDirection.ASC;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }
}
