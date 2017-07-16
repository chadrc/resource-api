package com.chadrc.resourceapi.basic;

import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Utils {
    private Utils() {
    }


    @SuppressWarnings("unchecked")
    public static Object convertParamValue(Parameter parameter, String paramName, Object paramValue, ResourceRepositorySet resourceRepositorySet) throws ResourceServiceThrowable {
        FromId fromId = parameter.getAnnotation(FromId.class);
        if (fromId != null
                && parameter.getName().equals(paramName)
                && paramValue != null
                && parameter.getType() != null
                && (paramValue instanceof String
                || (List.class.isAssignableFrom(parameter.getType())
                && List.class.isAssignableFrom(paramValue.getClass())))) {
            Object resource;
            if (paramValue instanceof String) {
                ResourceRepository typeRepository = resourceRepositorySet.getRepository(parameter.getType());
                if (typeRepository != null) {
                    String id = (String) paramValue;
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
                    List idList = (List) paramValue;
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
