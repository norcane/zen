package com.norcane.zen.base;

import java.util.function.Predicate;

public final class Predicates {

    private Predicates() {
        // utility class - hence the private constructor
        throw new IllegalStateException();
    }

    public static <T> Predicate<T> alwaysTrue() {
        return value -> true;
    }
}
