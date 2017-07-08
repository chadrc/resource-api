package com.chadrc.resourceapi.core.exceptions;

public class ResourceNotFound extends ResourceServiceThrowable {

    public ResourceNotFound() {
        super(404);
    }
}
