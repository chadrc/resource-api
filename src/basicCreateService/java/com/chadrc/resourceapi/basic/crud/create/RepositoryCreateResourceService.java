package com.chadrc.resourceapi.basic.crud.create;

import com.chadrc.resourceapi.basic.CRUDResult;
import com.chadrc.resourceapi.basic.ResourceRepository;
import com.chadrc.resourceapi.basic.ResourceRepositorySet;
import com.chadrc.resourceapi.basic.Utils;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

@RequestMapping(method = RequestMethod.POST)
public class RepositoryCreateResourceService implements ResourceService<CreateRequest> {

    private static Logger log = Logger.getLogger(RepositoryCreateResourceService.class);

    private ResourceRepositorySet resourceRepositorySet;

    @Autowired
    public void setResourceRepositorySet(ResourceRepositorySet resourceRepositorySet) {
        this.resourceRepositorySet = resourceRepositorySet;
    }

    @Override
    public Result fulfill(Class resourceType, CreateRequest request) throws ResourceServiceThrowable {
        ResourceRepository resourceRepository = resourceRepositorySet.getRepository(resourceType);
        Constructor[] constructors = resourceType.getConstructors();
        Constructor<?> selectedConstructor = null;
        for (Constructor<?> constructor : constructors) {
            if (constructor.getAnnotation(NoCreate.class) != null) {
                continue;
            }
            Parameter[] parameters = constructor.getParameters();
            if (Utils.parametersMatchRequest(parameters, request.getParamValues(), resourceRepositorySet)) {
                selectedConstructor = constructor;
                break;
            }
        }

        if (selectedConstructor != null) {
            try {
                Object obj = selectedConstructor.newInstance(Utils.extractValueArray(request.getParamValues()));
                resourceRepository.save(obj);
                return Resource.result(new CRUDResult(obj));
            } catch (InvocationTargetException invokeException) {
                if (invokeException.getCause() instanceof ResourceServiceThrowable) {
                    throw (ResourceServiceThrowable) invokeException.getCause();
                } else {
                    log.error("Failed to create resource.", invokeException);
                }
            } catch (Exception e) {
                log.error("Failed to create resource.", e);
            }
        } else {
            throw Resource.badRequest();
        }

        return null;
    }
}
