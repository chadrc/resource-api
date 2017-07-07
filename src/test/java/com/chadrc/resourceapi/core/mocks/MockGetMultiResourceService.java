package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.GetResourceService;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/multi")
public class MockGetMultiResourceService implements GetResourceService<GetMultiRequest> {
    @Override
    public Object fulfill(String resourceName, GetMultiRequest request) {
        return new DataResponse("Get Multi Result");
    }
}
