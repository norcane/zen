package com.norcane.zen.data;

import com.norcane.zen.test.UnitTestBase;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class MergeStrategiesTest extends UnitTestBase {

    @Test
    void testNonInstantiable() {
        assertThrows(InvocationTargetException.class, () -> coverageSingleton(MergeStrategies.class));
    }

    @Test
    void latter() {
        assertEquals("second", MergeStrategies.latter("first", "second"));
        assertEquals("second", MergeStrategies.latter(null, "second"));
        assertEquals("first", MergeStrategies.latter("first", null));
    }

    @Test
    void concatLists() {
        assertEquals(List.of("first", "second"), MergeStrategies.concatLists(List.of("first"), List.of("second")));
        assertEquals(List.of("first"), MergeStrategies.concatLists(List.of("first"), null));
        assertEquals(List.of("second"), MergeStrategies.concatLists(null, List.of("second")));
    }
}