package com.norcane.zen.io;

import java.util.Objects;

public class StringResource implements Resource {

    private static final String PATH = "<inline>";

    private final String content;

    public StringResource(final String content) {
        this.content = Objects.requireNonNull(content);
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public String readAsString() {
        return this.content;
    }
}
