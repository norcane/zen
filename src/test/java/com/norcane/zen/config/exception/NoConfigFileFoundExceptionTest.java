package com.norcane.zen.config.exception;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NoConfigFileFoundExceptionTest {

    private static final NoConfigFileFoundException exception = new NoConfigFileFoundException(List.of());

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