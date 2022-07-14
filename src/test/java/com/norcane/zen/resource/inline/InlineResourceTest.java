package com.norcane.zen.resource.inline;

import com.google.common.io.CharStreams;

import com.norcane.zen.resource.Resource;

import org.junit.jupiter.api.Test;

import java.io.Reader;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class InlineResourceTest {

    private static final String content = "Hello, there!";
    private static final Resource resource = Resource.inline("test", "txt", content);

    @Test
    void name() {
        assertEquals("test", resource.name());
    }

    @Test
    void type() {
        assertEquals("txt", resource.type());
    }

    @Test
    void location() {
        assertNotNull(resource.location());
    }

    @Test
    void reader() throws Exception {
        try (final Reader reader = resource.reader()) {
            assertEquals(content, CharStreams.toString(reader));
        }
    }

    @Test
    void writer() {
        assertThrows(UnsupportedOperationException.class, resource::writer);
    }

    @Test
    void readAsString() {
        assertEquals(content, resource.readAsString());
    }
}