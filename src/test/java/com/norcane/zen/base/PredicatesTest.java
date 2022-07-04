package com.norcane.zen.base;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import io.quarkus.test.junit.QuarkusTest;

import static com.norcane.zen.test.Assertions.assertNonInstantiable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class PredicatesTest {

    @Test
    void testNonInstantiable() {
        assertNonInstantiable(Predicates.class);
    }

    @Test
    void alwaysTrue() {
        final List<String> result = Stream.of("one", "two").filter(Predicates.alwaysTrue()).toList();
        assertEquals(List.of("one", "two"), result);
    }
}