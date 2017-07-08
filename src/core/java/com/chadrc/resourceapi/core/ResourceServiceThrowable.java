package com.chadrc.resourceapi.core;

public class ResourceServiceThrowable extends Throwable {
    private Object errorObject;
    private int status;

    protected ResourceServiceThrowable(int status) {
        this.status = status;
    }

    protected ResourceServiceThrowable(int status, Object errorObject) {
        this.status = status;
        this.errorObject = errorObject;
    }

    Object getErrorObject() {
        return errorObject;
    }

    int getStatus() {
        return status;
    }
}
