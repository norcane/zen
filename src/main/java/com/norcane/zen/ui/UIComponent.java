package com.norcane.zen.ui;

import com.norcane.zen.ui.console.Console;

/**
 * Represents <i>TUI</i> component, that can be rendered as text to the console.
 */
public interface UIComponent {

    /**
     * Render component as a text to the given {@link Console}.
     *
     * @param console console used for rendering
     */
    void render(Console console);
}
