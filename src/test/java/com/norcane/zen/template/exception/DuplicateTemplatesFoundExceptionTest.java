package com.norcane.zen.template.exception;

import org.junit.jupiter.api.Test;

import java.util.List;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class DuplicateTemplatesFoundExceptionTest {

    private final DuplicateTemplatesFoundException exception = new DuplicateTemplatesFoundException("java", List.of());

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