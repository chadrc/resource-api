package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;

@Service
public class MockPostResourceService implements PostResourceService {
    @Override
    public Result fulfill(GetRequest request) {
        return new Result("Post Result");
    }
}
