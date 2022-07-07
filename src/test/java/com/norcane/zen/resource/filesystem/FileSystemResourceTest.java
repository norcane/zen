package com.norcane.zen.resource.filesystem;


import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.CannotReadResourceException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

    @BeforeAll
    public static void init() throws Exception {
        final Path tempFile = Files.createTempFile(null, null);
        Files.writeString(tempFile, content);

        location = tempFile.toAbsolutePath().toString();
        resource = FileSystemResource.of(tempFile);
    }

    @Test
    void name() {
        assertEquals("name", FileSystemResource.of(Path.of("/foo/bar/name.txt")).name());
    }

    @Test
    void type() {
        assertEquals("txt", FileSystemResource.of(Path.of("/foo/bar/name.txt")).type());
    }

    @Test
    void location() {
        assertEquals(location, resource.location());
    }

    @Test
    void readAsString() {
        assertEquals(content, resource.readAsString());
        assertThrows(CannotReadResourceException.class, () -> FileSystemResource.of(Path.of("/not/existing")).readAsString());
    }

    @Test
    void testToString() {
        assertEquals("FileSystemResource[%s]".formatted(location), resource.toString());
    }
}