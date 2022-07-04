package com.norcane.zen.resource.inline;

import com.norcane.zen.resource.Resource;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class InlineResourceTest {

    private static final String content = "Hello, there!";
    private static final Resource resource = InlineResource.of(content);

    @Test
    void location() {
        assertNotNull(resource.location());
    }

    @Test
    void readAsString() {
        assertEquals(content, resource.readAsString());
    }
}