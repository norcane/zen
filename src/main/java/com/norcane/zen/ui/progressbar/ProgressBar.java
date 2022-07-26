package com.norcane.zen.ui.progressbar;

import com.norcane.zen.ui.InteractiveUIComponent;
import com.norcane.zen.ui.console.Console;
import com.norcane.zen.ui.progressbar.wrapped.ProgressBarWrappedIterable;

import java.util.Collection;
import java.util.function.Function;

/**
 * Represents {@link com.norcane.zen.ui.UIComponent} for progress bar.
 */
public interface ProgressBar extends InteractiveUIComponent {

    /**
     * Progresses one step in current progress bar status, with the given message to be shown.
     *
     * @param message message to be shown
     */
    void step(String message);

    /**
     * Wraps the input {@link Collection} and automatically tracks the progress and updates the underlying {@link ProgressBar}. See
     * {@link ProgressBarWrappedIterable} for more details.
     *
     * @param collection collection to wrap
     * @param messageFn  produces message displayed by progress bar for every item of {@link Iterable}
     * @param console    console used to render the progress bar
     * @return collection wrapper
     */
    static <T> ProgressBarWrappedIterable<T> wrap(final Collection<T> collection, final Function<T, String> messageFn, final Console console) {
        final ProgressBar progressBar = new ConciseProgressBar(collection.size(), "Processing...");
        return new ProgressBarWrappedIterable<>(collection, progressBar, messageFn, console);
    }
}
