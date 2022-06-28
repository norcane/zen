package com.norcane.zen.resource.inline;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class InlineResourceFactoryTest {

    @Inject
    InlineResourceFactory factory;

    @Test
    void getPrefixes() {
        assertNotNull(factory.getPrefixes());
    }

    @Test
    void getResource() {
        final String content = "Hello, there!";

        assertEquals(content, factory.getResource(content).readAsString());
    }

    @Test
    void getResources() {
        assertThrows(UnsupportedOperationException.class, () -> factory.getResources("foobar"));
    }
}