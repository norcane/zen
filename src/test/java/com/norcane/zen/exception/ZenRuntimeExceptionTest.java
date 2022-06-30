package com.norcane.zen.exception;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ZenRuntimeExceptionTest {

    private static final ZenRuntimeException exception = new ZenRuntimeException() {
        @Override
        protected String problem() {
            return "";
        }

        @Override
        protected String solution() {
            return "";
        }

        @Override
        protected List<String> links() {
            return List.of();
        }
    };

    @Test
    void toPretty() {
        assertNotNull(exception.toPretty());
    }
}