package com.chadrc.resourceapi.core;

import java.util.Map;

public interface OptionsProvider {
    Map<String, Object> getOptions(Class resourceType);
}
