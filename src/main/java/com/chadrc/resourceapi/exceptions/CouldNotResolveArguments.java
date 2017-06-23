package com.chadrc.resourceapi.exceptions;

public class CouldNotResolveArguments extends ResourceServiceException {
    public CouldNotResolveArguments(String resourceName) {
        super("Could not create " + resourceName + " with given arguments.");
    }
}
