package com.norcane.zen.resource;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class PathMatcherTest {

    @Inject
    PathMatcher pathMatcher;

    @Test
    void matches() {
        // test exact matching
        assertTrue(pathMatcher.matches("test", "test"));
        assertTrue(pathMatcher.matches(".test", ".test"));
        assertFalse(pathMatcher.matches(".test/jpg", "test/jpg"));
        assertFalse(pathMatcher.matches("test", ".test"));
        assertFalse(pathMatcher.matches(".test", "test"));

        // test matching with ?'s
        assertTrue(pathMatcher.matches("t?st", "test"));
        assertTrue(pathMatcher.matches("??st", "test"));
        assertTrue(pathMatcher.matches("tes?", "test"));
        assertTrue(pathMatcher.matches("te??", "test"));
        assertTrue(pathMatcher.matches("?es?", "test"));
        assertFalse(pathMatcher.matches("tes?", "tes"));
        assertFalse(pathMatcher.matches("tes?", "testt"));
        assertFalse(pathMatcher.matches("tes?", "tsst"));

        // test matching with *'s
        assertTrue(pathMatcher.matches("*", "test"));
        assertTrue(pathMatcher.matches("test*", "test"));
        assertTrue(pathMatcher.matches("test*", "testTest"));
        assertTrue(pathMatcher.matches("*test*", "AnothertestTest"));
        assertTrue(pathMatcher.matches("*test", "Anothertest"));
        assertTrue(pathMatcher.matches("*/*", "test/test"));
        assertTrue(pathMatcher.matches("test*aaa", "testblaaaa"));
        assertFalse(pathMatcher.matches("test*", "tst"));
        assertFalse(pathMatcher.matches("test*", "tsttest"));
        assertFalse(pathMatcher.matches("*test*", "tsttst"));
        assertFalse(pathMatcher.matches("*test", "tsttst"));
        assertFalse(pathMatcher.matches("*/*", "tsttst"));
        assertFalse(pathMatcher.matches("test*aaa", "test"));
        assertFalse(pathMatcher.matches("test*aaa", "testblaaab"));

        // test matching with ?'s and .'s
        assertTrue(pathMatcher.matches(".?", ".a"));
        assertTrue(pathMatcher.matches(".?.a", ".a.a"));
        assertTrue(pathMatcher.matches(".a.?", ".a.b"));
        assertTrue(pathMatcher.matches(".??.a", ".aa.a"));
        assertTrue(pathMatcher.matches(".a.??", ".a.bb"));
        assertTrue(pathMatcher.matches(".?", ".a"));

        // test matching with **'s
        assertTrue(pathMatcher.matches(".**", ".testing.testing"));
        assertTrue(pathMatcher.matches(".*.**", ".testing.testing"));
        assertTrue(pathMatcher.matches(".**.*", ".testing.testing"));
        assertTrue(pathMatcher.matches(".bla.**.bla", ".bla.testing.testing.bla"));
        assertTrue(pathMatcher.matches(".bla.**.bla", ".bla.testing.testing.bla.bla"));
        assertTrue(pathMatcher.matches(".**.test", ".bla.bla.test"));
        assertTrue(pathMatcher.matches(".bla.**.**.bla", ".bla.bla.bla.bla.bla.bla"));
        assertTrue(pathMatcher.matches(".bla*bla.test", ".blaXXXbla.test"));
        assertTrue(pathMatcher.matches(".*bla.test", ".XXXbla.test"));
        assertFalse(pathMatcher.matches(".bla*bla.test", ".blaXXXbl.test"));
        assertFalse(pathMatcher.matches(".*bla.test", "XXXblab.test"));
        assertFalse(pathMatcher.matches(".*bla.test", "XXXbl.test"));
    }

    @Test
    void isPattern() {
        assertTrue(pathMatcher.isPattern("t?st"));
        assertTrue(pathMatcher.isPattern("test*"));
        assertTrue(pathMatcher.isPattern("**.test"));
        assertFalse(pathMatcher.isPattern(".test/jpg"));
        assertFalse(pathMatcher.isPattern("test"));
    }
}