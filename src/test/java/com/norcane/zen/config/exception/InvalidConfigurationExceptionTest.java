package com.norcane.zen.config.exception;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class InvalidConfigurationExceptionTest {

    private static final InvalidConfigurationException exception = new InvalidConfigurationException(Set.of());

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