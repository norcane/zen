package com.norcane.zen.resource.filesystem;

import com.norcane.zen.resource.DefaultResourceFactory;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

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
    public void prefixes() {
        assertNotNull(factory.prefixes());
    }

    @Test
    public void resource() throws Exception {
        final String content = "Hello, there!";
        final Path tempFile = Files.createTempFile(null, null);
        Files.writeString(tempFile, content);

        final String location = tempFile.toAbsolutePath().toString();
        final Optional<Resource> resource = factory.resource(location);

        assertTrue(resource.isPresent());
        assertEquals(location, resource.get().location());
        assertEquals(content, resource.get().readAsString());
        assertTrue(factory.resource("/not/existing").isEmpty());
    }

    @Test
    public void resources() throws Exception {
        final Path tempDirectory = Files.createTempDirectory(null);
        final Path fileA = Path.of("a.txt");
        final Path fileB = Path.of("foo/b.txt");
        Files.createDirectories(tempDirectory.resolve(fileB.getParent()));
        Files.createFile(tempDirectory.resolve(fileA));
        Files.createFile(tempDirectory.resolve(fileB));

        final String pattern = tempDirectory + File.separator + "**.txt";

        // check if correctly resolves non-existing paths
        assertTrue(factory.resources("/not/existing", resource -> true).isEmpty());
        assertThrows(ResourceNotFoundException.class, () -> factory.findResources("/not/existing", "pattern", resource -> true));

        // check if correctly resolves directory path
        final List<Resource> resources1 = factory.resources(tempDirectory.toString(), resource -> true);
        assertEquals(2, resources1.size());
        assertTrue(resources1.stream().anyMatch(resource -> resource.location().endsWith("a.txt")));
        assertTrue(resources1.stream().anyMatch(resource -> resource.location().endsWith("b.txt")));

        // check if correctly resolves single file path
        final List<Resource> resources2 = factory.resources(tempDirectory.resolve(fileA).toString(), resource -> true);
        assertEquals(1, resources2.size());
        assertTrue(resources2.stream().anyMatch(resource -> resource.location().endsWith("a.txt")));

        // check if correctly resolves GLOB pattern
        final List<Resource> resources3 = factory.resources(pattern, resource -> true);
        assertEquals(2, resources3.size());
        assertTrue(resources3.stream().anyMatch(resource -> resource.location().endsWith("a.txt")));
        assertTrue(resources3.stream().anyMatch(resource -> resource.location().endsWith("b.txt")));
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

        final List<Resource> resources1 = factory.findResources(root, "**.txt", resource -> true);
        assertEquals(2, resources1.size());
        assertTrue(resources1.stream().anyMatch(resource -> resource.location().endsWith("a.txt")));
        assertTrue(resources1.stream().anyMatch(resource -> resource.location().endsWith("b.txt")));
    }

    @Test
    void resolveRootDir() {
        assertEquals("/WEB-INF/", factory.resolveRootDir("/WEB-INF/*.xml"));
        assertEquals("/Users/john/", factory.resolveRootDir("/Users/john/**/foo.xml"));
    }
}