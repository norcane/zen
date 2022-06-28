package com.norcane.zen.resource.exception;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class ResourceNotFoundExceptionTest {

    private static final ResourceNotFoundException exception = new ResourceNotFoundException("foo");

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