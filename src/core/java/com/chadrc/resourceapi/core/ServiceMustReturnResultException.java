package com.chadrc.resourceapi.core;

class ServiceMustReturnResultException extends Exception {
    ServiceMustReturnResultException() {
        super("Resource services must return a non-null result. Use Resource.emptyResult() for a null return value.");
    }
}
