package com.norcane.zen.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class UnitTestBase {

    protected <S> void coverageSingleton(Class<S> singletonClass)
        throws SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        final Constructor<S> constructor = singletonClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
