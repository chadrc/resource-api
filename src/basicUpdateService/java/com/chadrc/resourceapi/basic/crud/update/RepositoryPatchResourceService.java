package com.chadrc.resourceapi.basic.crud.update;

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

@RequestMapping(method = RequestMethod.PATCH)
public class RepositoryPatchResourceService implements ResourceService<PatchRequest> {

    private static Logger log = Logger.getLogger(RepositoryPatchResourceService.class);

    private ResourceRepositorySet resourceRepositorySet;

    @Autowired
    public void setResourceRepositorySet(ResourceRepositorySet resourceRepositorySet) {
        this.resourceRepositorySet = resourceRepositorySet;
    }

    @Override
    public Result fulfill(Class resourceType, PatchRequest request) throws ResourceServiceThrowable {
        ResourceRepository resourceRepository = resourceRepositorySet.getRepository(resourceType);
        Object resource = resourceRepository.findOne(request.getId());
        Method[] methods = resourceType.getMethods();
        try {
            for (String fieldName : request.getFields().keySet()) {
                Object value = request.getFields().get(fieldName);
                String transformed = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method setterMethod = null;
                for (Method method : methods) {
                    if (method.getDeclaringClass() != Object.class
                            && method.getName().startsWith("set")
                            && method.getName().substring(3).equals(transformed)
                            && method.getParameterCount() == 1
                            && method.getParameterTypes()[0] == value.getClass()) {
                        setterMethod = method;
                    }
                }

                if (setterMethod != null) {
                    setterMethod.invoke(resource, value);
                } else {
                    throw Resource.badRequest();
                }
            }

            resourceRepository.save(resource);
            return Resource.emptyResult();
        } catch (InvocationTargetException invocationException) {
            if (invocationException.getCause() instanceof ResourceServiceThrowable) {
                throw (ResourceServiceThrowable) invocationException.getCause();
            }
            log.error("Failed to invoke setter:", invocationException);
        } catch (Exception e) {
            log.error("Failed to invoke setter:", e);
        }
        return null;
    }
}
