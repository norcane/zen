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
class ResourceLoaderTest {

    @Inject
    ResourceLoader resourceLoader;

    @Test
    void getResource() {
        final Resource resource = resourceLoader.getResource("classpath:/classpath-resource.txt");

        assertEquals("/classpath-resource.txt", resource.getLocation());
    }

    @Test
    void getResources() throws Exception {
        final Path tempDirectory = Files.createTempDirectory(null);
        final Path fileA = Path.of("a.txt");
        final Path fileB = Path.of("foo/b.txt");
        Files.createDirectories(tempDirectory.resolve(fileB.getParent()));
        Files.createFile(tempDirectory.resolve(fileA));
        Files.createFile(tempDirectory.resolve(fileB));

        final List<Resource> resources = resourceLoader.getResources(tempDirectory.toString());
        assertEquals(1, resources.size());
        assertTrue(resources.stream().anyMatch(resource -> resource.getLocation().endsWith("a.txt")));

    }

    @Test
    void removePrefixes() {
        final List<String> prefixes = List.of("classpath");
        assertEquals("/foo/bar", resourceLoader.removePrefixes("classpath:/foo/bar", prefixes));
    }
}