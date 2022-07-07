package com.norcane.zen.resource.classpath;

import com.norcane.zen.resource.Resource;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class ClassPathResourceFactoryTest {

    @Inject
    ClassPathResourceFactory factory;

    @Test
    void prefixes() {
        assertNotNull(factory.prefixes());
    }

    @Test
    void resource() {
        final String location = "/classpath-resource.txt";

        final Optional<Resource> resource = factory.resource(location);
        assertTrue(resource.isPresent());
        assertEquals(location, resource.get().location());

        assertTrue(factory.resource("foobar").isEmpty());
    }

    @Test
    void resources() {
        assertThrows(UnsupportedOperationException.class, () -> factory.resources("foo", resource -> true));
    }
}