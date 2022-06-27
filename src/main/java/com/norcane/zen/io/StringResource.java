package com.norcane.zen.io;

import java.util.Objects;

public final class StringResource implements Resource {

    public static final String PREFIX = "inline:";
    private static final String PATH = "<inline>";

    private final String string;

    private StringResource(final String string) {
        this.string = string;
    }

    public static StringResource of(final String string) {
        Objects.requireNonNull(string);
        return new StringResource(string.startsWith(PREFIX) ? string.substring(PREFIX.length()) : string);

    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public String getLocation() {
        return PATH;
    }

    @Override
    public String readAsString() {
        return this.string;
    }
}
