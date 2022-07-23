package com.norcane.zen.exception;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class UnexpectedBehaviorExceptionTest {

    final UnexpectedBehaviorException exception = new UnexpectedBehaviorException("uh oh!");
    final UnexpectedBehaviorException exceptionWithCause = new UnexpectedBehaviorException("uh oh!", new IOException());

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

    @Test
    void getCause() {
        assertTrue(exceptionWithCause.getCause() instanceof IOException);
    }
}