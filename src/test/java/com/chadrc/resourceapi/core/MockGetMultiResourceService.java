package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping(path = "/multi")
public class MockGetMultiResourceService implements GetResourceService<GetMultiRequest> {
    @Override
    public Result fulfill(GetMultiRequest request) {
        return new Result("Get Multi Result");
    }
}
