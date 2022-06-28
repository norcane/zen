package com.norcane.zen.resource.filesystem;

import com.norcane.zen.resource.DefaultResourceFactory;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class FileSystemResourceFactoryTest {

    @Inject
    @DefaultResourceFactory
    FileSystemResourceFactory factory;

    @Test
    public void getPrefixes() {
        assertNotNull(factory.getPrefixes());
    }

    @Test
    public void getResource() throws Exception {
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
    public void getResources() {
        assertThrows(UnsupportedOperationException.class, () -> factory.getResources("foo"));
    }

    @Test
    public void findResources() throws Exception {
        final String root = Paths.get(getClass().getResource("/resources-test").toURI()).toString();

        final List<Resource> paths1 = factory.findResources(root, "**/*.txt");
        assertEquals(2, paths1.size());
        assertTrue(paths1.get(0).getLocation().endsWith("a.txt"));
        assertTrue(paths1.get(1).getLocation().endsWith("b.txt"));

        final List<Resource> paths2 = factory.findResources(root, "**/a.txt");
        assertEquals(1, paths2.size());
        assertTrue(paths2.get(0).getLocation().endsWith("a.txt"));
    }
}