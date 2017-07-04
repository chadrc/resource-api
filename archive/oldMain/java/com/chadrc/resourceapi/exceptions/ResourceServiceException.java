package com.chadrc.resourceapi.exceptions;

public class ResourceServiceException extends Exception {
    public ResourceServiceException() {

    }

    ResourceServiceException(String message) {
        super(message);
    }
}
