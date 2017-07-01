package com.chadrc.resourceapi.exceptions;

public class CannotInvokeActionWithoutResource extends ResourceServiceException {
    public CannotInvokeActionWithoutResource(String actionName) {
        super("Cannot use action '" + actionName + "' without providing a resource as a target.");
    }
}
