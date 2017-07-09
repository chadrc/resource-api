package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceOptionsProvider;
import com.chadrc.resourceapi.core.ResourceOptionsSection;
import com.chadrc.resourceapi.core.models.Saga;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OptionsProvider implements ResourceOptionsProvider {
    @Override
    public ResourceOptionsSection getOptions(Class resourceType) {
        return Resource.options()
                .add("get", true)
                .add("delete", resourceType != Saga.class);
    }
}
