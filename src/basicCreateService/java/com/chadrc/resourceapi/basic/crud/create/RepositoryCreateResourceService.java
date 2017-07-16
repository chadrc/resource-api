package com.chadrc.resourceapi.basic.crud.create;

import com.chadrc.resourceapi.basic.CRUDResult;
import com.chadrc.resourceapi.basic.ResourceRepository;
import com.chadrc.resourceapi.basic.ResourceRepositorySet;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.*;
import java.util.ArrayList;
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

    @SuppressWarnings("unchecked")
    private boolean typesMatchFieldValues(Parameter[] parameters, List<CreateParameter> fieldValues) throws ResourceServiceThrowable {
        if (parameters.length != fieldValues.size()) {
            return false;
        }
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            CreateParameter createParameter = fieldValues.get(i);
            Object value = createParameter.getValue();
            Object convertedValue = convertParamValue(parameter, createParameter.getName(), createParameter.getValue(), value);
            createParameter.setValue(convertedValue);

            if (createParameter.getValue() != null
                    && parameter.getType() != createParameter.getValue().getClass()
                    && !(parameter.getType() == List.class && List.class.isAssignableFrom(createParameter.getValue().getClass()))) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private Object convertParamValue(Parameter parameter, String paramName, Object paramValue, Object value) throws ResourceServiceThrowable {
        FromId fromId = parameter.getAnnotation(FromId.class);
        if (fromId != null
                && parameter.getName().equals(paramName)
                && paramValue != null
                && parameter.getType() != null
                && (value instanceof String
                || (List.class.isAssignableFrom(parameter.getType())
                && List.class.isAssignableFrom(paramValue.getClass())))) {
            Object resource;
            if (value instanceof String) {
                ResourceRepository typeRepository = resourceRepositorySet.getRepository(parameter.getType());
                if (typeRepository != null) {
                    String id = (String) value;
                    resource = typeRepository.findOne(id);
                    if (resource == null && fromId.mustExist()) {
                        throw Resource.badRequest();
                    }
                } else {
                    throw Resource.badRequest();
                }
            } else {
                Type listType = ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[0];
                ResourceRepository typeRepository = resourceRepositorySet.getRepository((Class) listType);
                if (typeRepository != null) {
                    List idList = (List) value;
                    Iterable resources = typeRepository.findAll(idList);
                    List resourceList = new ArrayList();
                    resources.forEach(resourceList::add);
                    resource = resourceList;
                } else {
                    throw Resource.badRequest();
                }
            }

            return resource;
        } else if (fromId == null
                && parameter.getName().equals(paramName)
                && paramValue != null
                && ((Map.class.isAssignableFrom(paramValue.getClass())
                && !Map.class.isAssignableFrom(parameter.getType()))
                || (List.class.isAssignableFrom(paramValue.getClass())
                && List.class.isAssignableFrom(parameter.getType())))) {
            Object obj;
            ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
            if (Map.class.isAssignableFrom(paramValue.getClass())) {
                obj = mapper.convertValue(paramValue, parameter.getType());
            } else {
                List list = (List) paramValue;
                List newList = new ArrayList();
                Type listType = ((ParameterizedType) parameter.getParameterizedType()).getActualTypeArguments()[0];
                if (!(listType instanceof Class)) {
                    throw Resource.badRequest();
                }
                for (Object item : list) {
                    newList.add(mapper.convertValue(item, (Class) listType));
                }
                obj = newList;
            }
            if (obj == null) {
                throw Resource.badRequest();
            }

            return obj;
        }

        return paramValue;
    }
}
