package com.norcane.zen.resource.filesystem;

import com.norcane.zen.resource.DefaultResourceFactory;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class FileSystemResourceFactoryTest {

    @Inject
    @DefaultResourceFactory
    FileSystemResourceFactory factory;

    @Test
    void getPrefixes() {
        assertNotNull(factory.getPrefixes());
    }

    @Test
    void getResource() throws Exception {
        final String content = "Hello, there!";
        final Path tempFile = Files.createTempFile(null, null);
        Files.writeString(tempFile, content);

        final String location = tempFile.toAbsolutePath().toString();
        final Resource resource = factory.getResource(location);

        assertEquals(location, resource.getLocation());
        assertEquals(content, resource.readAsString());
        assertThrows(ResourceNotFoundException.class, () -> factory.getResource("/not/existing"));
    }

    @Test
    void getResources() {
        assertThrows(UnsupportedOperationException.class, () -> factory.getResources("foo"));
    }
}