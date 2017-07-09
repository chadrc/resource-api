package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.ResourceOptionsProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OptionsProvider implements ResourceOptionsProvider {
    @Override
    public Map<String, Object> getOptions(Class resourceType) {
        Map<String, Object> options = new HashMap<>();
        options.put("get", true);
        options.put("delete", true);
        return options;
    }
}
