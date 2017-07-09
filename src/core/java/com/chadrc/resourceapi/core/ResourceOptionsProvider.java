package com.chadrc.resourceapi.core;

import java.util.Map;

public interface ResourceOptionsProvider {
    Map<String, Object> getOptions(Class resourceType);
}
