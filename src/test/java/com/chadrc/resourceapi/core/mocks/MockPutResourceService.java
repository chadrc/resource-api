package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(method = RequestMethod.PUT)
public class MockPutResourceService implements ResourceService<PutRequest> {
    @Override
    public Result fulfill(Class resourceType, PutRequest request) throws ResourceServiceThrowable {
        if ("return null".equals(request.getInfo())) {
            return null;
        }
        return Resource.result(new DataResponse("Put Result"));
    }
}
