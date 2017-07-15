package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.core.utils.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResourceRepositorySet {
    private Map<Class, ResourceRepository> resourceRepositoriesByClass = new HashMap<>();

    @Autowired(required = false)
    public void setResourceRepositories(List<ResourceRepository> resourceRepositories) {
        for (ResourceRepository resourceRepository : resourceRepositories) {
            Type[] typeArgs = ReflectionUtils.getTypeArgsForTypeFromObject(ResourceRepository.class, resourceRepository);
            resourceRepositoriesByClass.put((Class) typeArgs[0], resourceRepository);
        }
    }

    public ResourceRepository getRepository(Class c) {
        return resourceRepositoriesByClass.get(c);
    }
}
