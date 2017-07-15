package com.chadrc.resourceapi.basic.crud.get;

import com.chadrc.resourceapi.basic.ResourceRepository;
import com.chadrc.resourceapi.core.GetResourceService;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryGetResourceService implements GetResourceService<GetRequest> {

    private Map<Class, ResourceRepository> resourceRepositoriesByClass = new HashMap<>();

    @Autowired
    public void setResourceRepositories(List<ResourceRepository> resourceRepositories) {
        for (ResourceRepository resourceRepository : resourceRepositories) {
            Class repositoryClass = resourceRepository.getClass();
            Type[] interfaces = ((Class)repositoryClass.getGenericInterfaces()[0]).getGenericInterfaces();
            Type resourceRepositoryInterface = null;
            for (Type inter : interfaces) {
                if (inter instanceof ParameterizedType && ((ParameterizedType)inter).getRawType() == ResourceRepository.class) {
                    resourceRepositoryInterface = inter;
                }
            }

            if (resourceRepositoryInterface != null && resourceRepositoryInterface instanceof ParameterizedType) {
                Type[] typeArgs = ((ParameterizedType) resourceRepositoryInterface).getActualTypeArguments();
                Class resourceClass = (Class) typeArgs[0];
                resourceRepositoriesByClass.put(resourceClass, resourceRepository);
            }
        }
    }

    @Override
    public Result fulfill(Class resourceType, GetRequest request) throws ResourceServiceThrowable {
        Object resource = resourceRepositoriesByClass.get(resourceType).findOne(request.getId());
        if (resource == null) {
            throw Resource.notFound();
        }
        return Resource.result(resource);
    }
}
