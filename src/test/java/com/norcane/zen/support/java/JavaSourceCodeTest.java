package com.norcane.zen.support.java;

import com.norcane.zen.resource.ResourceManager;
import com.norcane.zen.source.SourceCode;
import com.norcane.zen.source.syntax.CStyleBlockCommentSyntax;
import com.norcane.zen.source.syntax.CommentSyntax;

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
    static final CommentSyntax COMMENT_SYNTAX = CStyleBlockCommentSyntax.instance();

    @Inject
    ResourceManager resourceManager;

    @Test
    void header() {
        final SourceCode sourceCode = new JavaSourceCode(COMMENT_SYNTAX, resourceManager.resource(sample));
        final SourceCode.Header expected = new SourceCode.Header(1, 3, List.of("/*", " * This is header", " */"));

        assertEquals(Optional.of(expected), sourceCode.header());
    }

    @Test
    void variables() {
        final SourceCode sourceCode = new JavaSourceCode(COMMENT_SYNTAX, resourceManager.resource(sample));

        assertTrue(sourceCode.variables().isEmpty());
    }
}