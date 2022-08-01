package com.norcane.zen.source.headersyntax;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class CStyleBlockHeaderSyntaxTest {

    final HeaderSyntax syntax = new CStyleBlockHeaderSyntax();

    @Test
    void isStart() {
        assertTrue(syntax.isStart("/*"));
        assertTrue(syntax.isStart("/* foo bar */"));
        assertTrue(syntax.isStart("/**"));
    }

    @Test
    void isEnd() {
        assertTrue(syntax.isEnd("*/"));
        assertTrue(syntax.isEnd("/* foo bar */"));
        assertTrue(syntax.isEnd("foo bar */"));
    }
}