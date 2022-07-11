package com.norcane.zen.resource;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class ResourceManagerTest {

    @Inject
    ResourceManager resourceManager;

    @Test
    void resource() {
        final Resource resource = resourceManager.resource("classpath:/classpath-resource.txt");

        assertEquals("/classpath-resource.txt", resource.location());
    }

    @Test
    void resources() throws Exception {
        final Path tempDirectory = Files.createTempDirectory(null);
        final Path fileA = Path.of("a.txt");
        final Path fileB = Path.of("foo/b.txt");
        Files.createDirectories(tempDirectory.resolve(fileB.getParent()));
        Files.createFile(tempDirectory.resolve(fileA));
        Files.createFile(tempDirectory.resolve(fileB));

        final List<Resource> resources = resourceManager.resources(tempDirectory.toString());
        assertEquals(2, resources.size());
        assertTrue(resources.stream().anyMatch(resource -> resource.location().endsWith("a.txt")));
        assertTrue(resources.stream().anyMatch(resource -> resource.location().endsWith("b.txt")));

    }

    @Test
    void removePrefixes() {
        final List<String> prefixes = List.of("classpath");
        assertEquals("/foo/bar", resourceManager.removePrefixes("classpath:/foo/bar", prefixes));
    }
}