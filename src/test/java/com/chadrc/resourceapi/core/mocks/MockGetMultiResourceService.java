package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(path = "/multi", method = RequestMethod.GET)
public class MockGetMultiResourceService implements ResourceService<GetMultiRequest> {
    @Override
    public Result fulfill(Class resourceType, GetMultiRequest request) throws ResourceServiceThrowable {
        return Resource.result(new DataResponse("Get Multi Result"));
    }
}
