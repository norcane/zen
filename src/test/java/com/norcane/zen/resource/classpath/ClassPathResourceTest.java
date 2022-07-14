package com.norcane.zen.resource.classpath;


import com.google.common.io.CharStreams;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.CannotReadResourceException;

import org.junit.jupiter.api.Test;

import java.io.Reader;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class ClassPathResourceTest {

    static final String content = "Hello there!";
    static final String location = "/classpath-resource.txt";
    static final Resource resource = Resource.classPath(location);

    @Test
    void name() {
        assertEquals("classpath-resource", resource.name());
    }

    @Test
    void type() {
        assertEquals("txt", resource.type());
    }

    @Test
    void location() {
        assertEquals(location, resource.location());
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
        assertThrows(CannotReadResourceException.class, () -> Resource.classPath("/not/existing").readAsString());
    }
}