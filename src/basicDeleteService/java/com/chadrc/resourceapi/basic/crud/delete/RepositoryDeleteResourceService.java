package com.chadrc.resourceapi.basic.crud.delete;

import com.chadrc.resourceapi.basic.ResourceRepository;
import com.chadrc.resourceapi.basic.ResourceRepositorySet;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(method = RequestMethod.DELETE)
public class RepositoryDeleteResourceService implements ResourceService<DeleteRequest> {

    private static Logger log = Logger.getLogger(RepositoryDeleteResourceService.class);

    private ResourceRepositorySet resourceRepositorySet;

    @Autowired
    public void setResourceRepositorySet(ResourceRepositorySet resourceRepositorySet) {
        this.resourceRepositorySet = resourceRepositorySet;
    }

    @Override
    public Result fulfill(Class resourceType, DeleteRequest request) throws ResourceServiceThrowable {
        if (StringUtils.isEmpty(request.getId())) {
            throw Resource.badRequest();
        }
        ResourceRepository resourceRepository = resourceRepositorySet.getRepository(resourceType);
        resourceRepository.delete(request.getId());
        return Resource.emptyResult();
    }
}
