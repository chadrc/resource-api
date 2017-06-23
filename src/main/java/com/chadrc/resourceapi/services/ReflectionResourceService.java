package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.exceptions.CouldNotResolveArguments;
import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.annotations.ResourceModel;
import com.chadrc.resourceapi.options.CreateOptions;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service("reflectionResourceService")
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

    @Override
    public Object create(CreateOptions options) throws ResourceServiceException {

        Class c = resourcesByName.get(options.getResourceName());
        Constructor<?>[] constructors = c.getDeclaredConstructors();

        Constructor<?> selectedConstructor = null;
        for (Constructor<?> constructor : constructors) {
            Type[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length != options.getArguments().size()) {
                continue;
            }

            boolean allMatch = true;
            for (int i=0; i<paramTypes.length; i++) {
                if (paramTypes[i] != options.getArguments().get(i).getValue().getClass()) {
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
            Object[] args = new Object[options.getArguments().size()];
            for (int i=0; i<options.getArguments().size(); i++) {
                args[i] = options.getArguments().get(i).getValue();
            }

            try {
                Object obj = selectedConstructor.newInstance(args);
                log.info("Created: " + obj);
                return obj;
            } catch (Exception e) {
                log.error("Failed to create resource.", e);
            }
        } else {
            throw new CouldNotResolveArguments(options.getResourceName());
        }

        throw new ResourceServiceException();
    }
}
