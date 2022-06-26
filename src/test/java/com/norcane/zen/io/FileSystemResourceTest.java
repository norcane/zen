package com.norcane.zen.io;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class FileSystemResourceTest {

    private static final String content = "Hello, there!";
    private static String path;
    private static Resource resource;

    @BeforeAll
    public static void init() throws Exception {
        final Path tempFile = Files.createTempFile(null, null);
        Files.writeString(tempFile, content);

        path = tempFile.toAbsolutePath().toString();
        resource = new FileSystemResource(tempFile);
    }

    @Test
    public void testExists() {
        assertTrue(resource.exists());
    }

    @Test
    public void testGetPath() {
        assertEquals(path, resource.getPath());
    }

    @Test
    public void testReadAsString() {
        assertEquals(content, resource.readAsString());
    }
}
