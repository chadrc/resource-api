package com.chadrc.resourceapi.basic.crud.create;

import com.chadrc.resourceapi.basic.CRUDResult;
import com.chadrc.resourceapi.basic.ResourceRepository;
import com.chadrc.resourceapi.basic.ResourceRepositorySet;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

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
            if (typesMatchFieldValues(parameters, request.getParamValues())) {
                selectedConstructor = constructor;
                break;
            }
        }

        if (selectedConstructor != null) {
            try {
                Object obj = selectedConstructor.newInstance(collectArgValues(request.getParamValues()));
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

    private Object[] collectArgValues(List<CreateParameter> fieldValues) {
        Object[] args = new Object[fieldValues.size()];
        for (int i = 0; i < fieldValues.size(); i++) {
            args[i] = fieldValues.get(i).getValue();
        }
        return args;
    }

    private boolean typesMatchFieldValues(Parameter[] parameters, List<CreateParameter> fieldValues) throws ResourceServiceThrowable {
        if (parameters.length != fieldValues.size()) {
            return false;
        }
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            CreateParameter createParameter = fieldValues.get(i);
            Object value = createParameter.getValue();
            FromId fromId = parameter.getAnnotation(FromId.class);
            if (fromId != null
                    && parameter.getType() != null
                    && value instanceof String
                    && createParameter.getName().endsWith("Id")) {
                String argName = createParameter.getName().replace("Id", "");
                if (parameter.getName().equals(argName)) {
                    ResourceRepository typeRepository = resourceRepositorySet.getRepository(parameter.getType());
                    if (typeRepository != null) {
                        String id = (String) value;
                        Object resource = typeRepository.findOne(id);
                        if (resource == null && fromId.mustExist()) {
                            throw Resource.badRequest();
                        }
                        createParameter.setValue(resource);
                    }
                }
            } else if (parameter.getName().equals(createParameter.getName())
                    && Map.class.isAssignableFrom(createParameter.getValue().getClass())
                    && !Map.class.isAssignableFrom(parameter.getType())) {
                Object obj = Jackson2ObjectMapperBuilder.json().build().convertValue(createParameter.getValue(), parameter.getType());
                if (obj == null) {
                    throw Resource.badRequest();
                }

                createParameter.setValue(obj);
            }

            if (createParameter.getValue() != null && parameters[i].getType() != createParameter.getValue().getClass()) {
                return false;
            }
        }
        return true;
    }
}
