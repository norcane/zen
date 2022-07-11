package com.norcane.zen.support.java;

import com.norcane.zen.resource.ResourceManager;
import com.norcane.zen.source.SourceCode;
import com.norcane.zen.source.header.CStyleBlockHeaderSyntax;
import com.norcane.zen.source.header.HeaderSyntax;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class JavaSourceCodeTest {

    static final String sample = "classpath:/sources/java/sample-block-multi.java.txt";
    static final HeaderSyntax headerSyntax = new CStyleBlockHeaderSyntax();

    @Inject
    ResourceManager resourceManager;

    @Test
    void header() {
        final SourceCode sourceCode = new JavaSourceCode(headerSyntax, resourceManager.resource(sample));
        final SourceCode.Header expected = new SourceCode.Header(1, 3, List.of("/*", " * This is header", " */"));

        assertEquals(Optional.of(expected), sourceCode.header());
    }

    @Test
    void variables() {
        final SourceCode sourceCode = new JavaSourceCode(headerSyntax, resourceManager.resource(sample));

        assertTrue(sourceCode.variables().isEmpty());
    }
}