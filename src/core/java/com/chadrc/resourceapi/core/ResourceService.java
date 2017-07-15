package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;

@Service
public interface ResourceService<T> {
    Result fulfill(Class resourceType, T request) throws ResourceServiceThrowable;
}
