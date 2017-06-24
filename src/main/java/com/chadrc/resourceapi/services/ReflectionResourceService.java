package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.exceptions.CouldNotResolveArguments;
import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.annotations.ResourceModel;
import com.chadrc.resourceapi.options.FieldValue;
import com.chadrc.resourceapi.options.PagingInfo;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class ReflectionResourceService implements ResourceService {

    private static Logger log = Logger.getLogger(ReflectionResourceService.class);

    private static Map<String, Class> resourcesByName = new HashMap<>();

    static {
        Reflections reflections = new Reflections("com.chadrc.resourceapi");
        Set<Class<?>> resourceModels = reflections.getTypesAnnotatedWith(ResourceModel.class);

        for (Class model : resourceModels) {
            log.info("Registering Model: " + model.getName() + " as " + model.getSimpleName());
            resourcesByName.put(model.getSimpleName(), model);
        }
    }

    private final ResourceStore resourceStore;

    @Autowired
    public ReflectionResourceService(ResourceStore resourceStore) {
        this.resourceStore = resourceStore;
    }

    @Override
    public Map<String, Object> options(String resourceName) {
        List<String> models = new ArrayList<>();

        for (Class model : resourcesByName.values()) {
            models.add(model.getCanonicalName());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("models", StringUtils.arrayToDelimitedString(models.toArray(), ","));
        return map;
    }

    @Override
    public Object create(String resourceName, List<FieldValue> arguments) throws ResourceServiceException {

        Class c = resourcesByName.get(resourceName);
        Constructor<?>[] constructors = c.getDeclaredConstructors();

        Constructor<?> selectedConstructor = null;
        for (Constructor<?> constructor : constructors) {
            Type[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length != arguments.size()) {
                continue;
            }

            boolean allMatch = true;
            for (int i=0; i<paramTypes.length; i++) {
                if (paramTypes[i] != arguments.get(i).getValue().getClass()) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                selectedConstructor = constructor;
                break;
            }
        }

        if (selectedConstructor != null) {
            Object[] args = new Object[arguments.size()];
            for (int i=0; i<arguments.size(); i++) {
                args[i] = arguments.get(i).getValue();
            }

            try {
                Object obj = selectedConstructor.newInstance(args);
                log.info("Created: " + obj);
                resourceStore.saveNew(c, obj);
                return obj;
            } catch (Exception e) {
                log.error("Failed to create resource.", e);
            }
        } else {
            throw new CouldNotResolveArguments(resourceName);
        }

        throw new ResourceServiceException();
    }

    @Override
    public Object getById(String resourceName, String id) throws ResourceServiceException {
        Class c = resourcesByName.get(resourceName);
        return resourceStore.getById(c, id);
    }

    @Override
    public Object getList(String resourceName, PagingInfo pagingInfo) throws ResourceServiceException {
        Class c = resourcesByName.get(resourceName);
        return resourceStore.getList(c, pagingInfo);
    }
}
