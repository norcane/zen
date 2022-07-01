package com.norcane.zen.data;

import java.util.List;
import java.util.stream.Stream;

/**
 * Collection of various methods representing merging strategies. Designed to be used within the {@link Mergeable#merge(Object)}.
 */
public final class MergeStrategies {

    private MergeStrategies() {
        // utility class - hence the private constructor
        throw new IllegalStateException();
    }

    /**
     * From the two given objects, return the second one if not {@code null}, else return first one.
     *
     * @param first first object
     * @param second second object
     * @param <T> type of the object
     * @return selected object
     */
    public static <T> T latter(final T first, final T second) {
        return second != null ? second : first;
    }

    /**
     * Return new list created by concatenating two given lists. This method is <i>null-safe</i>: if any of the input lists is {@code null}, it will be
     * considered as empty list.
     *
     * @param first first list
     * @param second second list
     * @param <T> type of the list elements
     * @return concatenated list
     */
    public static <T> List<T> concatLists(final List<T> first, final List<T> second) {
        return Stream
            .concat(first != null ? first.stream() : Stream.empty(), second != null ? second.stream() : Stream.empty())
            .toList();
    }
}
