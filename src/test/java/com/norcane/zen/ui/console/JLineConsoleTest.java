package com.norcane.zen.ui.console;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class JLineConsoleTest {

    @Inject
    JLineConsole console;

    @Test
    void print() {
        console.print("Hello, world!");
    }

    @Test
    void printLn() {
        console.printLn("Hello, world!");
    }
}