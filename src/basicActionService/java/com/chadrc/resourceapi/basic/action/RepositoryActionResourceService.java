package com.chadrc.resourceapi.basic.action;

import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(path = "/action", method = RequestMethod.POST)
public class RepositoryActionResourceService implements ResourceService<ActionRequest> {
    @Override
    public Result fulfill(Class resourceType, ActionRequest request) throws ResourceServiceThrowable {
        return null;
    }
}
