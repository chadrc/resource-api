package com.chadrc.resourceapi.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static Type[] getTypeArgsForTypeFromObject(Class searchClass, Object obj) {
        Type resourceRepositoryInterface = findInterfaceOnObject(searchClass, obj.getClass());
        if (resourceRepositoryInterface != null && resourceRepositoryInterface instanceof ParameterizedType) {
            return ((ParameterizedType) resourceRepositoryInterface).getActualTypeArguments();
        }
        return new Type[0];
    }

    private static Type findInterfaceOnObject(Class searchClass, Class baseClass) {
        for (Type inter : baseClass.getGenericInterfaces()) {
            if (inter instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) inter;
                if (parameterizedType.getRawType() == searchClass) {
                    return inter;
                } else {
                    Type inherited = findInterfaceOnObject(searchClass, (Class) parameterizedType.getRawType());
                    if (inherited != null) {
                        return inherited;
                    }
                }
            } else if (inter instanceof Class) {
                Type inherited = findInterfaceOnObject(searchClass, (Class) inter);
                if (inherited != null) {
                    return inherited;
                }
            }
        }
        return null;
    }
}