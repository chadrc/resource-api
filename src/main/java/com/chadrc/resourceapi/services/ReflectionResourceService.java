package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.exceptions.CouldNotResolveArguments;
import com.chadrc.resourceapi.exceptions.ResourceServiceException;
import com.chadrc.resourceapi.annotations.ResourceModel;
import com.chadrc.resourceapi.exceptions.ResourceTypeDoesNotExist;
import com.chadrc.resourceapi.controller.FieldValue;
import com.chadrc.resourceapi.controller.PagingInfo;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.*;
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
    public OptionsResult options(String resourceName) {
        List<String> models = new ArrayList<>();

        for (Class model : resourcesByName.values()) {
            models.add(model.getCanonicalName());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("models", StringUtils.arrayToDelimitedString(models.toArray(), ","));
        return new OptionsResult(map);
    }

    @Override
    public ActionResult action(ActionClause clause) throws ResourceServiceException {
        Class c = getResourceType(clause.getResourceName());
        Method[] methods = c.getMethods();

        Method selectedMethod = null;
        for (Method method : methods) {
            Type[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != clause.getArguments().size()) {
                continue;
            }

            if (method.getName().equals(clause.getActionName())
                    && typesMatchFieldValues(paramTypes, clause.getArguments())) {
                selectedMethod = method;
                break;
            }
        }

        if (selectedMethod != null) {
            try {
                Object obj = selectedMethod.invoke(null, collectArgValues(clause.getArguments()));
                if (obj == null) {
                    Map<String, Boolean> fillResult = new HashMap<>();
                    fillResult.put("success", true);
                    obj = fillResult;
                }
                return new ActionResult(obj);
            } catch (Exception e) {
                log.error("Failed to perform action: " + clause.getResourceName() + "." + clause.getActionName() );
            }
        } else {
            throw new CouldNotResolveArguments(clause.getResourceName());
        }

        throw new ResourceServiceException();
    }

    @Override
    public CreateResult create(String resourceName, List<FieldValue> arguments) throws ResourceServiceException {
        Class c = getResourceType(resourceName);
        Constructor<?>[] constructors = c.getConstructors();

        Constructor<?> selectedConstructor = null;
        for (Constructor<?> constructor : constructors) {
            Type[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length != arguments.size()) {
                continue;
            }

            if (typesMatchFieldValues(paramTypes, arguments)) {
                selectedConstructor = constructor;
                break;
            }
        }

        if (selectedConstructor != null) {
            try {
                Object obj = selectedConstructor.newInstance(collectArgValues(arguments));
                log.info("Created: " + obj);
                resourceStore.saveNew(c, obj);
                return new CreateResult(obj);
            } catch (Exception e) {
                log.error("Failed to create resource.", e);
            }
        } else {
            throw new CouldNotResolveArguments(resourceName);
        }

        throw new ResourceServiceException();
    }

    @Override
    public GetResult get(String resourceName, String id) throws ResourceServiceException {
        return new GetResult(resourceStore.getById(getResourceType(resourceName), id));
    }

    @Override
    public ListResult getList(String resourceName, PagingInfo pagingInfo) throws ResourceServiceException {
        return new ListResult(resourceStore.getList(getResourceType(resourceName), pagingInfo));
    }

    private Object[] collectArgValues(List<FieldValue> fieldValues) {
        Object[] args = new Object[fieldValues.size()];
        for (int i=0; i<fieldValues.size(); i++) {
            args[i] = fieldValues.get(i).getValue();
        }
        return args;
    }

    private boolean typesMatchFieldValues(Type[] types, List<FieldValue> fieldValues) {
        for (int i=0; i<types.length; i++) {
            if (types[i] != fieldValues.get(i).getValue().getClass()) {
                return false;
            }
        }
        return true;
    }

    private Class getResourceType(String resourceName) throws ResourceServiceException {
        Class c = resourcesByName.get(resourceName);
        if (c == null) {
            throw new ResourceTypeDoesNotExist("Resource type with name '" + resourceName + "' does not exist.");
        }
        return c;
    }
}
