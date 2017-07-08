package com.chadrc.resourceapi.core;

public abstract class ResourceServiceThrowable extends Throwable {
    private Object errorObject = null;
    private int status = 400;

    ResourceServiceThrowable() {

    }

    protected ResourceServiceThrowable(int status) {
        this.status = status;
    }

    protected ResourceServiceThrowable(int status, Object errorObject) {
        this.status = status;
        this.errorObject = errorObject;
    }

    public Object getErrorObject() {
        return errorObject;
    }

    public int getStatus() {
        return status;
    }
}
