package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.GetResourceService;
import com.chadrc.resourceapi.core.exceptions.ResourceServiceThrowable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/multi")
public class MockGetMultiResourceService implements GetResourceService<GetMultiRequest> {
    @Override
    public Object fulfill(String resourceName, GetMultiRequest request) throws ResourceServiceThrowable {
        return new DataResponse("Get Multi Result");
    }
}
