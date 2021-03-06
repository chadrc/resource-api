package com.chadrc.resourceapi.basic.crud.update;

import com.chadrc.resourceapi.basic.RequestParameter;
import com.chadrc.resourceapi.basic.ResourceRepository;
import com.chadrc.resourceapi.basic.ResourceRepositorySet;
import com.chadrc.resourceapi.basic.Utils;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

@RequestMapping(method = RequestMethod.PATCH)
public class RepositoryPatchResourceService implements ResourceService<PatchRequest> {

    private static Logger log = Logger.getLogger(RepositoryPatchResourceService.class);

    private ResourceRepositorySet resourceRepositorySet;

    @Autowired
    public void setResourceRepositorySet(ResourceRepositorySet resourceRepositorySet) {
        this.resourceRepositorySet = resourceRepositorySet;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result fulfill(Class resourceType, PatchRequest request) throws ResourceServiceThrowable {
        if (StringUtils.isEmpty(request.getId())) {
            throw Resource.badRequest();
        }
        ResourceRepository resourceRepository = resourceRepositorySet.getRepository(resourceType);
        Object resource = resourceRepository.findOne(request.getId());
        if (resource == null) {
            throw Resource.badRequest();
        }
        Method[] methods = resourceType.getMethods();
        try {
            for (String fieldName : request.getFields().keySet()) {
                RequestParameter requestParameter = request.getFields().get(fieldName);
                Object value = requestParameter.getValue();
                String transformed = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method setterMethod = null;
                for (Method method : methods) {
                    if (method.getDeclaringClass() != Object.class
                            && method.getAnnotation(NoUpdate.class) == null
                            && method.getName().startsWith("set")
                            && method.getName().substring(3).equals(transformed)
                            && method.getParameterCount() == 1) {
                        Parameter parameter = method.getParameters()[0];
                        if (requestParameter.getName() != null
                                && !requestParameter.getName().equals(parameter.getName())) {
                            continue;
                        }
                        value = Utils.convertParamValue(parameter, value, resourceRepositorySet);
                        if (value == null
                                || method.getParameterTypes()[0] == value.getClass()
                                || (parameter.getType() == List.class && List.class.isAssignableFrom(value.getClass()))) {
                            setterMethod = method;
                        }
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
