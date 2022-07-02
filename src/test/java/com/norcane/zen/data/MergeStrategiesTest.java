package com.norcane.zen.data;

import org.junit.jupiter.api.Test;

import java.util.List;

import io.quarkus.test.junit.QuarkusTest;

import static com.norcane.zen.test.Assertions.assertNonInstantiable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class MergeStrategiesTest {

    @Test
    void testNonInstantiable() {
        assertNonInstantiable(MergeStrategies.class);
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