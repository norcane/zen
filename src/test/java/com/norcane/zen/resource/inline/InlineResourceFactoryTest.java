package com.norcane.zen.resource.inline;

import com.norcane.zen.resource.Resource;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class InlineResourceFactoryTest {

    @Inject
    InlineResourceFactory factory;

    @Test
    void prefixes() {
        assertNotNull(factory.prefixes());
    }

    @Test
    void resource() {
        final String location = "test:txt:Hello, there!";

        final Optional<Resource> resource = factory.resource(location);
        assertTrue(resource.isPresent());
        assertEquals("test", resource.get().name());
        assertEquals("txt", resource.get().type());
        assertEquals("Hello, there!", resource.get().readAsString());
    }

    @Test
    void resources() {
        assertThrows(UnsupportedOperationException.class, () -> factory.resources("foobar", resource -> true));
    }
}