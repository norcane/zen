package com.norcane.zen.config.exception;

import org.junit.jupiter.api.Test;

import java.util.List;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class MultipleConfigFilesFoundExceptionTest {

    private static final MultipleConfigFilesFoundException exception = new MultipleConfigFilesFoundException(List.of());

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