package com.norcane.zen.resource;

import org.junit.jupiter.api.Test;

import java.util.List;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void getResources() {
    }

    @Test
    void removePrefixes() {
        final List<String> prefixes = List.of("classpath");
        assertEquals("/foo/bar", resourceLoader.removePrefixes("classpath:/foo/bar", prefixes));
    }
}