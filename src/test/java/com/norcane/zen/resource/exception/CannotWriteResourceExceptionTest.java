package com.norcane.zen.resource.exception;

import com.norcane.zen.resource.Resource;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class CannotWriteResourceExceptionTest {

    static final Resource resource = Resource.inline("test", "txt", "test");
    static final CannotWriteResourceException exception = new CannotWriteResourceException(resource, null);

    @Test
    void problem() {
        assertNotNull(exception.problem());
    }

    @Test
    void solution() {
        assertNotNull(exception.solution());
    }

    @Test
    void links() {
        assertNotNull(exception.links());
    }
}