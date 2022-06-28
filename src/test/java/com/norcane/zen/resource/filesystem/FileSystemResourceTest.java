package com.norcane.zen.resource.filesystem;


import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.CannotReadResourceException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    void getLocation() {
        assertEquals(location, resource.getLocation());
    }

    @Test
    void readAsString() {
        assertEquals(content, resource.readAsString());
        assertThrows(CannotReadResourceException.class, () -> FileSystemResource.of(Paths.get("/not/existing")).readAsString());
    }
}