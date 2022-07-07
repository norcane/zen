package com.norcane.zen.resource.inline;

import com.norcane.zen.resource.Resource;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class InlineResourceTest {

    private static final String content = "Hello, there!";
    private static final Resource resource = InlineResource.of(content);

    @Test
    void name() {
        assertThrows(UnsupportedOperationException.class, resource::name);
    }

    @Test
    void type() {
        assertThrows(UnsupportedOperationException.class, resource::type);
    }

    @Test
    void location() {
        assertNotNull(resource.location());
    }

    @Test
    void readAsString() {
        assertEquals(content, resource.readAsString());
    }
}