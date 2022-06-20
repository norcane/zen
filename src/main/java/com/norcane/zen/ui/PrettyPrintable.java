package com.norcane.zen.ui;

/**
 * Represents an object that can be pretty printed. Compared to {@link Object#toString()}, this one should specifically produce human-friendly textual
 * representation, used for example to display object in text console output or <i>GUI</i>.
 */
public interface PrettyPrintable {

    /**
     * Returns human-friendly, textual representation of current object.
     *
     * @return pretty-printed representation
     */
    String toPretty();
}
