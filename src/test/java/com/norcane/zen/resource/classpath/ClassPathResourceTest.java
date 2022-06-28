package com.norcane.zen.resource.classpath;


import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.CannotReadResourceException;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class ClassPathResourceTest {

    private static final String location = "/classpath-resource.txt";
    private static final Resource resource = ClassPathResource.of(location);

    @Test
    void getLocation() {
        assertEquals(location, resource.getLocation());
    }

    @Test
    void readAsString() {
        assertEquals("Hello there!", resource.readAsString());
        assertThrows(CannotReadResourceException.class, () -> ClassPathResource.of("/not/existing").readAsString());
    }
}