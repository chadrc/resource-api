package com.chadrc.resourceapi.service;

import com.chadrc.resourceapi.exceptions.*;
import com.chadrc.resourceapi.controller.FieldValue;
import com.chadrc.resourceapi.controller.PagingInfo;
import com.chadrc.resourceapi.store.ResourceStore;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.*;
import java.util.*;

@Service
public class ReflectionResourceService implements ResourceService {

    private static Logger log = Logger.getLogger(ReflectionResourceService.class);

    private Map<String, Class> resourcesByName = new HashMap<>();

    private final ResourceStore resourceStore;

    @Autowired
    public ReflectionResourceService(ResourceStore resourceStore,
                                     List<ResourceModel> resourceModels) {
        this.resourceStore = resourceStore;

        for (com.chadrc.resourceapi.service.ResourceModel model : resourceModels) {
            Class c = model.getClass();
            log.info("Registering Model: " + c.getName() + " as " + c.getSimpleName());
            resourcesByName.put(c.getSimpleName(), c);
        }
    }

    @Override
    public OptionsResult options() {
        Map<String, ResourceOptions> map = new HashMap<>();
        for (Class model : resourcesByName.values()) {
            map.put(model.getSimpleName(), new ResourceOptions());
        }

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

            if (method.getAnnotation(Action.class) != null
                    && method.getName().equals(clause.getActionName())
                    && typesMatchFieldValues(paramTypes, clause.getArguments())) {
                selectedMethod = method;
                break;
            }
        }

        if (selectedMethod != null) {
            try {
                Object target = null;
                if (!StringUtils.isEmpty(clause.getResourceId())) {
                    target = resourceStore.getById(c, clause.getResourceId());
                    if (target == null) {
                        throw new TargetResourceNotFound(clause.getResourceId());
                    }
                } else if (!Modifier.isStatic(selectedMethod.getModifiers())) {
                    throw new CannotInvokeActionWithoutResource(clause.getActionName());
                }

                Object obj = selectedMethod.invoke(target, collectArgValues(clause.getArguments()));
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
            throw new ResourceTypeDoesNotExist(resourceName);
        }
        return c;
    }
}
