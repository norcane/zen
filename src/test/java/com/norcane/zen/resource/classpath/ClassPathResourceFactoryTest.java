package com.norcane.zen.resource.classpath;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class ClassPathResourceFactoryTest {

    @Inject
    ClassPathResourceFactory factory;

    @Test
    void getPrefixes() {
        assertNotNull(factory.getPrefixes());
    }

    @Test
    void getResource() {
        final String location = "/classpath-resource.txt";

        final Resource resource = factory.getResource(location);
        assertEquals(location, resource.getLocation());

        assertThrows(ResourceNotFoundException.class, () -> factory.getResource("foobar"));
    }

    @Test
    void getResources() {
        assertThrows(UnsupportedOperationException.class, () -> factory.getResources("foo"));
    }
}