package com.chadrc.resourceapi.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResourceRepositorySet {
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

    public ResourceRepository getRepository(Class c) {
        return resourceRepositoriesByClass.get(c);
    }
}
