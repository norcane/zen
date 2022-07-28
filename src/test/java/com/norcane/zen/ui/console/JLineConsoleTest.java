package com.norcane.zen.ui.console;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class JLineConsoleTest {

    @InjectSpy
    JLineConsole console;

    @Test
    void print() {
        console.print("Hello, world!");
    }

    @Test
    void printLn() {
        console.printLn("Hello, world!");
    }

    @Test
    void clearLine() {

        // -- mocks
        when(console.cursorMovementSupported()).thenReturn(true);

        console.clearLine();

        // -- verify
        verify(console).cursorMovementSupported();
        verify(console, times(2)).print(any());
    }

    @Test
    void cursorMovementSupported() {
        console.cursorMovementSupported();
    }
}