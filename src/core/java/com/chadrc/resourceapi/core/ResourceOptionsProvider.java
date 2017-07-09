package com.chadrc.resourceapi.core;

public interface ResourceOptionsProvider {
    ResourceOptionsSection getOptions(Class resourceType);
}
