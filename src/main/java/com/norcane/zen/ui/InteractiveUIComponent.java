package com.norcane.zen.ui;

import com.norcane.zen.ui.console.Console;

/**
 * Version of {@link UIComponent} that provides some sort of interactivity based on changes of some input variables. Example of such component
 * <i>progress bar</i>, that might need to re-render itself when progress is changed. Any re-rendering needs to be done inside
 * {@link UIComponent#render(Console)} method. If the component needs to perform some final cleanup at the end of its lifespan, {@link #cleanup(Console)} should
 * be responsible for that.
 */
public interface InteractiveUIComponent extends UIComponent {

    /**
     * Perform final cleanup at the end of component lifecycle.
     *
     * @param console console used to render/cleanup the component
     */
    void cleanup(Console console);
}
