package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.PatchResourceService;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.Result;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class MockPatchResourceService implements PatchResourceService<PatchRequest> {

    @Override
    public Result fulfill(Class resourceType, PatchRequest request) throws ResourceServiceThrowable {
        if (StringUtils.isEmpty(request.getInfo())) {
            Map<String, String> errors = new HashMap<>();
            errors.put("info", "Cannot be empty.");
            throw new PatchError(errors);
        }
        return Resource.result(new DataResponse("Patch Result"));
    }
}
