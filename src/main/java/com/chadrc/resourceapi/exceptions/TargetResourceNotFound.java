package com.chadrc.resourceapi.exceptions;

public class TargetResourceNotFound extends ResourceServiceException {
    public TargetResourceNotFound(String resourceId) {
        super("Could not find target resource identified by '" + resourceId + "'.");
    }
}
