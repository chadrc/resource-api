package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(method = RequestMethod.DELETE)
public class MockDeleteResourceService implements ResourceService<DeleteRequest> {
    @Override
    public Result fulfill(Class resourceType, DeleteRequest request) throws ResourceServiceThrowable {
        return Resource.result(new DataResponse("Delete Result"));
    }
}
