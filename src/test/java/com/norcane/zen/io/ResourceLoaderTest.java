package com.norcane.zen.io;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class ResourceLoaderTest {

    private static String fileSystemPath;

    @Inject
    ResourceLoader resourceLoader;

    @BeforeAll
    public static void init() throws Exception {
        final Path tempFile = Files.createTempFile(null, null);
        Files.writeString(tempFile, "Hello, there!");

        fileSystemPath = tempFile.toAbsolutePath().toString();
    }

    @Test
    public void testGetResource() {
        final String classPathPath = "classpath:/classpath-resource.txt";

        assertTrue(resourceLoader.getResource(classPathPath).exists());
        assertTrue(resourceLoader.getResource(fileSystemPath).exists());
    }
}
