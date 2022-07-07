package com.norcane.zen.base;

/**
 * Represents preferably immutable class, that might use <i>memoization</i> to lazily initialize some of its object internal state.
 */
public interface Memoizable {

    /**
     * Resets all possible memoized state of the object.
     */
    void resetMemoizedState();
}
