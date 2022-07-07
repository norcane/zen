package com.norcane.zen.support.impl;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
class JavaSupportTest {

    @Inject
    JavaSupport javaSupport;

    @Test
    void name() {
        assertEquals("java", javaSupport.name());
    }

    @Test
    void fileExtensions() {
        assertFalse(javaSupport.fileExtensions().isEmpty());
    }
}