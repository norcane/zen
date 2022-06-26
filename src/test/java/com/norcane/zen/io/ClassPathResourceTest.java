package com.norcane.zen.io;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class ClassPathResourceTest {

    private final String path = "/classpath-resource.txt";
    private final Resource resource = ClassPathResource.of(path);

    @Test
    public void testExists() {
        assertTrue(resource.exists());
    }

    @Test
    public void testGetPath() {
        assertEquals(path, resource.getPath());
    }

    @Test
    public void testReadAsString() {
        assertEquals("Hello there!", resource.readAsString());
    }
}
