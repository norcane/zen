package com.norcane.zen.ui;

import com.norcane.zen.meta.SemVer;

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
        console.error("Error!");
        console.error(SemVer.from("1.2.3"));
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
        console.print(SemVer.from("1.2.3"));

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
}