package com.chadrc.resourceapi.basic.crud.create;

public class CreateResult {
    private Object created;

    public CreateResult(Object created) {
        this.created = created;
    }

    public Object getCreated() {
        return created;
    }

    public void setCreated(Object created) {
        this.created = created;
    }
}
