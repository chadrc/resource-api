package com.chadrc.resourceapi.exceptions;

public class ResourceTypeDoesNotExist extends ResourceServiceException {
    public ResourceTypeDoesNotExist(String resourceName) {
        super("Resource type with name '" + resourceName + "' does not exist.");
    }
}
