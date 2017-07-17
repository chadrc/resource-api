package com.chadrc.resourceapi.basic.action;

import com.chadrc.resourceapi.basic.CRUDResult;
import com.chadrc.resourceapi.basic.ResourceRepository;
import com.chadrc.resourceapi.basic.ResourceRepositorySet;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequestMapping(path = "/action", method = RequestMethod.POST)
public class RepositoryActionResourceService implements ResourceService<ActionRequest> {

    private static Logger log = Logger.getLogger(RepositoryActionResourceService.class);

    private ResourceRepositorySet resourceRepositorySet;

    @Autowired
    public void setResourceRepositorySet(ResourceRepositorySet resourceRepositorySet) {
        this.resourceRepositorySet = resourceRepositorySet;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result fulfill(Class resourceType, ActionRequest request) throws ResourceServiceThrowable {
        ResourceRepository resourceRepository = resourceRepositorySet.getRepository(resourceType);
        Method selectedMethod = null;
        for (Method method : resourceType.getMethods()) {
            if (method.getAnnotation(Action.class) != null
                    && method.getName().equals(request.getName())) {
                selectedMethod = method;
                break;
            }
        }

        try {
            Object target = null;
            if (request.getId() != null) {
                target = resourceRepository.findOne(request.getId());
                if (target == null) {
                    throw Resource.notFound();
                }
            }
            Object result = selectedMethod.invoke(target);
            return Resource.result(new CRUDResult(result));
        } catch (InvocationTargetException invokeException) {
            //
        } catch (Exception e) {
            log.error("Failed to call action: " + request.getName(), e);
        }

        return null;
    }
}
