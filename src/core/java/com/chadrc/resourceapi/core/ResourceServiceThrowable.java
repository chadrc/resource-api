package com.chadrc.resourceapi.core;

public class ResourceServiceThrowable extends Throwable {
    private Object errorObject = null;
    private int status = 400;

    ResourceServiceThrowable() {

    }

    ResourceServiceThrowable(int status) {
        this.status = status;
    }

    ResourceServiceThrowable(int status, Object errorObject) {
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
