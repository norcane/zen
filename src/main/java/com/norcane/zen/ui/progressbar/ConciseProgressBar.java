package com.norcane.zen.ui.progressbar;

import com.norcane.zen.ui.console.Console;

public class ConciseProgressBar implements ProgressBar {

    private static final char[] SPINNER_FRAMES = new char[]{'-', '\\', '|', '/'};
    private static final int SPINNER_MODULO = SPINNER_FRAMES.length;

    private final int maximum;

    private int current;
    private String message;

    public ConciseProgressBar(int maximum, String message) {
        this.current = 0;
        this.maximum = maximum;
        this.message = message;
    }

    @Override
    public void render(Console console) {
        final char spinner = SPINNER_FRAMES[current % SPINNER_MODULO];
        final String currentFormat = "%" + String.valueOf(maximum).length() + "s";
        final String currentSpinner = console.cursorMovementSupported() ? "@|bold,magenta %s|@ ".formatted(spinner) : "";

        final String rendered = "%s@|bold [%s of %d]|@ %s".formatted(currentSpinner, currentFormat.formatted(current), maximum, message);

        if (console.cursorMovementSupported()) {
            console.clearLine();
            console.print(rendered);    // do not print newlines so updated progress bar can replace old
        } else {
            console.printLn(rendered);  // print every update on new line
        }
    }

    @Override
    public void step(final String message) {
        this.current++;
        this.message = message;
    }

    @Override
    public void cleanup(Console console) {
        console.clearLine();
    }
}
