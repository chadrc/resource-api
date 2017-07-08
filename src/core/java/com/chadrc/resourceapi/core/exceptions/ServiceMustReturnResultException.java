package com.chadrc.resourceapi.core.exceptions;

public class ServiceMustReturnResultException extends Exception {
    public ServiceMustReturnResultException() {
        super("Resource services must return a non-null result. Use Resource.emptyResult() for a null return value.");
    }
}
