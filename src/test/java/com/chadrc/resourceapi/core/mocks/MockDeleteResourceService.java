package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.*;

import java.util.HashMap;
import java.util.Map;

public class MockDeleteResourceService implements DeleteResourceService<DeleteRequest>, OptionsProvider {
    @Override
    public Result fulfill(String resourceName, DeleteRequest request) throws ResourceServiceThrowable {
        return Resource.result(new DataResponse("Delete Result"));
    }

    @Override
    public Map<String, Object> getOptions(Class resourceType) {
        Map<String, Object> options = new HashMap<>();
        options.put("delete", true);
        return options;
    }
}
