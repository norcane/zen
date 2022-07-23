package com.norcane.zen.ui;

import com.norcane.zen.exception.UnexpectedBehaviorException;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class ConsoleTest {

    @InjectSpy
    Console console;

    @Test
    void emptyLine() {
        console.emptyLine();
    }

    @Test
    void error() {
        console.error(new UnexpectedBehaviorException("Uh oh!"));
    }

    @Test
    void message() {
        console.message("Heading", "text", "blue");
    }

    @Test
    void print_enabled() {
        // -- mocks
        when(console.isEnabled()).thenReturn(true);

        console.print("Print");

        verify(console).ansiString("Print");
    }

    @Test
    void print_disabled() {
        // -- mocks
        when(console.isEnabled()).thenReturn(false);

        console.print("Print");

        // -- verify
        verify(console, never()).ansiString("Print");
    }

    @Test
    void width() {
        console.width();
    }
}