package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.GetResourceService;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.Result;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/multi")
public class MockGetMultiResourceService implements GetResourceService<GetMultiRequest> {
    @Override
    public Result fulfill(String resourceName, GetMultiRequest request) throws ResourceServiceThrowable {
        return Resource.result(new DataResponse("Get Multi Result"));
    }
}
