package com.norcane.zen.ui.progressbar.wrapped;

import com.norcane.zen.ui.console.Console;
import com.norcane.zen.ui.progressbar.ProgressBar;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * Wrapper over existing {@link Iterable} that automatically tracks the progress and properly updates the underlying {@link ProgressBar}. This is useful to be
 * used together with for example <i>enhanced for loops</i>.
 *
 * @param <T> type of iterator items
 */
public class ProgressBarWrappedIterable<T> implements Iterable<T> {

    private final Iterator<T> underlying;
    private final ProgressBar progressBar;
    private final Function<T, String> messageFn;
    private final Console console;

    /**
     * Constructs new instance for given {@link Iterable} and {@link ProgressBar}.
     *
     * @param iterable    iterable to wrap
     * @param progressBar progress bar used to track the progress
     * @param messageFn   produces message displayed by progress bar for every item of {@link Iterable}
     * @param console     console used to render the progress bar
     */
    public ProgressBarWrappedIterable(final Iterable<T> iterable,
                                      final ProgressBar progressBar,
                                      final Function<T, String> messageFn,
                                      final Console console) {

        this.underlying = Objects.requireNonNull(iterable).iterator();
        this.progressBar = Objects.requireNonNull(progressBar);
        this.messageFn = Objects.requireNonNull(messageFn);
        this.console = Objects.requireNonNull(console);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            @Override
            public boolean hasNext() {
                final boolean hasNext = underlying.hasNext();

                if (!hasNext) {
                    progressBar.cleanup(console);
                }

                return hasNext;
            }

            @Override
            public T next() {
                final T next = underlying.next();

                progressBar.step(messageFn.apply(next));
                progressBar.render(console);

                return next;
            }
        };
    }
}
