package com.chadrc.resourceapi.core;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/multi")
public class MockGetMultiResourceService implements GetResourceService<GetMultiRequest> {
    @Override
    public Result fulfill(String resourceName, GetMultiRequest request) {
        return new Result("Get Multi Result");
    }
}
