package com.norcane.zen.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class Assertions {

    private Assertions() {
        // utility class - hence the private constructor
        throw new IllegalStateException();
    }

    public static <S> void assertNonInstantiable(Class<S> singletonClass) {

        final Constructor<S> constructor;

        try {
            constructor = singletonClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        constructor.setAccessible(true);

        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }
}
