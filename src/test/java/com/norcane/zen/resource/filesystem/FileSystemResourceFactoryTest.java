package com.norcane.zen.resource.filesystem;

import com.norcane.zen.resource.DefaultResourceFactory;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public void getResources() throws Exception {
        final Path tempDirectory = Files.createTempDirectory(null);
        final Path fileA = Path.of("a.txt");
        final Path fileB = Path.of("foo/b.txt");
        Files.createDirectories(tempDirectory.resolve(fileB.getParent()));
        Files.createFile(tempDirectory.resolve(fileA));
        Files.createFile(tempDirectory.resolve(fileB));

        final String pattern = tempDirectory + File.separator + "**.txt";

        // check if correctly resolves non-existing paths
        assertTrue(factory.getResources("/not/existing").isEmpty());
        assertThrows(ResourceNotFoundException.class, () -> factory.findResources("/not/existing", "pattern"));

        // check if correctly resolves directory path
        final List<Resource> resources1 = factory.getResources(tempDirectory.toString());
        assertEquals(1, resources1.size());
        assertTrue(resources1.stream().anyMatch(resource -> resource.getLocation().endsWith("a.txt")));

        // check if correctly resolves single file path
        final List<Resource> resources2 = factory.getResources(tempDirectory.resolve(fileA).toString());
        assertEquals(1, resources2.size());
        assertTrue(resources2.stream().anyMatch(resource -> resource.getLocation().endsWith("a.txt")));

        // check if correctly resolves GLOB pattern
        final List<Resource> resources3 = factory.getResources(pattern);
        assertEquals(2, resources3.size());
        assertTrue(resources3.stream().anyMatch(resource -> resource.getLocation().endsWith("a.txt")));
        assertTrue(resources3.stream().anyMatch(resource -> resource.getLocation().endsWith("b.txt")));
    }

    @Test
    public void findResources() throws Exception {
        final Path tempDirectory = Files.createTempDirectory(null);
        final Path fileA = Path.of("a.txt");
        final Path fileB = Path.of("foo/b.txt");
        Files.createDirectories(tempDirectory.resolve(fileB.getParent()));
        Files.createFile(tempDirectory.resolve(fileA));
        Files.createFile(tempDirectory.resolve(fileB));

        final String root = tempDirectory.toString();

        final List<Resource> resources1 = factory.findResources(root, "**.txt");
        assertEquals(2, resources1.size());
        assertTrue(resources1.stream().anyMatch(resource -> resource.getLocation().endsWith("a.txt")));
        assertTrue(resources1.stream().anyMatch(resource -> resource.getLocation().endsWith("b.txt")));
    }

    @Test
    void resolveRootDir() {
        assertEquals("/WEB-INF/", factory.resolveRootDir("/WEB-INF/*.xml"));
        assertEquals("/Users/john/", factory.resolveRootDir("/Users/john/**/foo.xml"));
    }
}