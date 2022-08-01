package com.norcane.zen.source.syntax;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class CStyleBlockCommentSyntaxTest {

    final CommentSyntax syntax = CStyleBlockCommentSyntax.instance();

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