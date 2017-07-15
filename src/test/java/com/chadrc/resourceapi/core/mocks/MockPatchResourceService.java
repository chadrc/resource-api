package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(method = RequestMethod.PATCH)
public class MockPatchResourceService implements ResourceService<PatchRequest> {

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
