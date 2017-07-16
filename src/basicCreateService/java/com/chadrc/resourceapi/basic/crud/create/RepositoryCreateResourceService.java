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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

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
            Type[] paramTypes = constructor.getParameterTypes();
            Annotation[][] paramAnnotations = constructor.getParameterAnnotations();
            if (typesMatchFieldValues(paramTypes, request.getParamValues(), paramAnnotations)) {
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

    private Object[] collectArgValues(List<Object> fieldValues) {
        Object[] args = new Object[fieldValues.size()];
        for (int i = 0; i < fieldValues.size(); i++) {
            args[i] = fieldValues.get(i);
        }
        return args;
    }

    private boolean typesMatchFieldValues(Type[] types, List<Object> fieldValues, Annotation[][] annotations) throws ResourceServiceThrowable {
        if (types.length != fieldValues.size()) {
            return false;
        }
        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            Annotation[] typeAnnotations = annotations[i];
            Object value = fieldValues.get(i);
            if (containsAnnotation(typeAnnotations, FromId.class)
                    && type instanceof Class
                    && value instanceof String) {
                ResourceRepository typeRepository = resourceRepositorySet.getRepository((Class) type);
                if (typeRepository != null) {
                    String id = (String) value;
                    Object resource = typeRepository.findOne(id);
                    if (resource == null && containsAnnotation(typeAnnotations, MustExist.class)) {
                        throw Resource.badRequest();
                    }
                    fieldValues.set(i, resource);
                }
            }

            if (types[i] != fieldValues.get(i).getClass()) {
                return false;
            }
        }
        return true;
    }

    private boolean containsAnnotation(Annotation[] annotations, Class<? extends Annotation> check) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == check) {
                return true;
            }
        }
        return false;
    }
}
