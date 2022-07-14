package com.norcane.zen.resource.filesystem;


import com.google.common.io.CharStreams;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.CannotReadResourceException;
import com.norcane.zen.resource.exception.CannotWriteResourceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class FileSystemResourceTest {

    private static final String content = "Hello, there!";
    private static String location;
    private static Resource resource;

    @BeforeEach
    void init() throws Exception {
        final Path tempFile = Files.createTempFile(null, null);
        Files.writeString(tempFile, content);

        location = tempFile.toAbsolutePath().toString();
        resource = Resource.file(tempFile);
    }

    @Test
    void name() {
        assertEquals("name", Resource.file("/foo/bar/name.txt").name());
    }

    @Test
    void type() {
        assertEquals("txt", Resource.file("/foo/bar/name.txt").type());
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
    void writer() throws Exception {
        final String newContent = "General Kenobi!";

        try (final Writer writer = resource.writer()) {
            writer.write(newContent);
        }

        try (final Reader reader = resource.reader()) {
            assertEquals(newContent, CharStreams.toString(reader));
        }

        assertThrows(CannotWriteResourceException.class, () -> {
            try (final Writer writer = Resource.file("/non/existing").writer()) {
                writer.write("foo bar");
            }
        });
    }

    @Test
    void readAsString() {
        assertEquals(content, resource.readAsString());
        assertThrows(CannotReadResourceException.class, () -> Resource.file("/not/existing").readAsString());
    }

    @Test
    void testToString() {
        assertEquals("FileSystemResource[%s]".formatted(location), resource.toString());
    }
}