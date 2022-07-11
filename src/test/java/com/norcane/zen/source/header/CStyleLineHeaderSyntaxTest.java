package com.norcane.zen.source.header;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class CStyleLineHeaderSyntaxTest {

    final HeaderSyntax syntax = new CStyleLineHeaderSyntax();

    @Test
    void isStart() {
        assertTrue(syntax.isStart("// foo bar"));
        assertFalse(syntax.isStart("hello // foo bar"));
        assertFalse(syntax.isStart("hello"));
    }

    @Test
    void isEnd() {
        assertTrue(syntax.isEnd("// foo bar"));
        assertFalse(syntax.isEnd("hello // foo bar"));
        assertFalse(syntax.isEnd("hello"));
    }
}