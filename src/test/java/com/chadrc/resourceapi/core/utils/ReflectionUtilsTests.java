package com.chadrc.resourceapi.core.utils;

import org.junit.Test;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;

interface GenericInterface<T> {
    Class<? extends T> getType();
}

interface GenericExtender extends GenericInterface<String> {

}

public class ReflectionUtilsTests {

    @Test
    public void genericTypeOfDirectImplementor() {
        DirectImplementor implementor = new DirectImplementor();
        Type[] types = ReflectionUtils.getTypeArgsForTypeFromObject(GenericInterface.class, implementor);
        assertEquals(types[0], Integer.class);
    }

    @Test
    public void genericTypeOfIndirectImplementor() {
        IndirectImplementor implementor = new IndirectImplementor();
        Type[] types = ReflectionUtils.getTypeArgsForTypeFromObject(GenericInterface.class, implementor);
        assertEquals(types[0], String.class);
    }
}

class DirectImplementor implements GenericInterface<Integer> {
    @Override
    public Class<? extends Integer> getType() {
        return Integer.class;
    }
}

class IndirectImplementor implements GenericExtender {
    @Override
    public Class<? extends String> getType() {
        return String.class;
    }
}