package com.norcane.zen.ui.progressbar;

import com.norcane.zen.ui.console.Console;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
class ConciseProgressBarTest {

    @InjectMock
    Console console;

    @Test
    void currentAndStep() {
        final ProgressBar progressBar = new ConciseProgressBar(100, "test");

        assertEquals(0, progressBar.current());
        progressBar.step("Another step");
        assertEquals(1, progressBar.current());

    }

    @Test
    void render_interactiveConsole() {
        final ProgressBar progressBar = new ConciseProgressBar(100, "test");

        // -- mocks
        when(console.cursorMovementSupported()).thenReturn(true);

        progressBar.render(console);

        // -- verify
        verify(console).cursorMovementSupported();
        verify(console).clearLine();
        verify(console).print("@|bold,magenta -|@ @|bold [  0 of 100]|@ test");
    }

    @Test
    void render_nonInteractiveConsole() {
        final ProgressBar progressBar = new ConciseProgressBar(100, "test");

        // -- mocks
        when(console.cursorMovementSupported()).thenReturn(false);

        progressBar.render(console);

        // -- verify
        verify(console).cursorMovementSupported();
        verify(console).printLn("@|bold [  0 of 100]|@ test");
    }

    @Test
    void cleanup() {
        final ProgressBar progressBar = new ConciseProgressBar(100, "test");

        progressBar.cleanup(console);

        // -- verify
        verify(console).clearLine();
    }
}