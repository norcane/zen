package com.norcane.zen.ui.console;

import com.norcane.zen.ui.UIComponent;

/**
 * Represents a wrapper over system terminal, used to access standard input and output, with few tweaks required by the appplication. Note that any
 * implementation must accept special ANSI color escape-codes, e.g. {@code @|bold,red Warning!|@}.
 *
 * @see <a href="https://fusesource.github.io/jansi/documentation/api/org/fusesource/jansi/AnsiRenderer.html">Jansi AnsiRenderer</a>
 */
public interface Console {

    /**
     * Prints text to console, without ending newline.
     *
     * @param text text to print
     */
    void print(String text);

    /**
     * Prints text to console, with ending newline.
     *
     * @param text text to print
     */
    void printLn(String text);

    /**
     * Clears current line if supported by underlying terminal implementation, otherwise does nothing.
     */
    void clearLine();

    /**
     * Detects whether current terminal implementation supports cursor movement (might be required for example by {@link Console#clearLine()}).
     *
     * @return {@code true} if cursor movement is supported
     */
    boolean cursorMovementSupported();

    /**
     * Prints empty line to the console.
     */
    default void emptyLine() {
        printLn("");
    }

    /**
     * Renders given {@link UIComponent} to the console.
     *
     * @param component component to render
     */
    default void render(final UIComponent component) {
        component.render(this);
    }
}
