package com.norcane.zen.resource.inline;

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
class InlineResourceFactoryTest {

    @Inject
    InlineResourceFactory factory;

    @Test
    void prefixes() {
        assertNotNull(factory.prefixes());
    }

    @Test
    void resource() {
        final String content = "Hello, there!";

        final Optional<Resource> resource = factory.resource(content);
        assertTrue(resource.isPresent());
        assertEquals(content, resource.get().readAsString());
    }

    @Test
    void resources() {
        assertThrows(UnsupportedOperationException.class, () -> factory.resources("foobar", resource -> true));
    }
}