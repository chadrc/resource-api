package com.chadrc.resourceapi.core;

class ResourceNotFound extends ResourceServiceThrowable {

    ResourceNotFound() {
        super(404);
    }
}
