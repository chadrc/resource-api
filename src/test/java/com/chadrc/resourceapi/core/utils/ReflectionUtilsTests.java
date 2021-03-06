package com.chadrc.resourceapi.core.utils;

import org.junit.Test;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;

interface GenericInterface<T> {
}

interface DirectExtender extends GenericInterface<String> {
}

public class ReflectionUtilsTests {

    @Test
    public void genericTypeOfDirectImplementor() {
        assertFoundTypeIsClass(new DirectImplementor(), Integer.class);
    }

    @Test
    public void genericTypeOfIndirectImplementor() {
        assertFoundTypeIsClass(new IndirectImplementor(), String.class);
    }

    private void assertFoundTypeIsClass(Object obj, Class assertClass) {
        Type[] types = ReflectionUtils.getTypeArgsForTypeFromObject(GenericInterface.class, obj);
        assertEquals(assertClass, types[0]);
    }
}

class DirectImplementor implements GenericInterface<Integer> {
}

class IndirectImplementor implements DirectExtender {
}
