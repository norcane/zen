package com.norcane.zen.resource.inline;

import com.norcane.zen.resource.Resource;

import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Objects;

public class InlineResource implements Resource {

    private static final String LOCATION = "<inline string>";

    private final String name;
    private final String type;
    private final String content;

    private InlineResource(final String name, final String type, final String content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public static InlineResource of(final String name, final String type, final String content) {
        return new InlineResource(Objects.requireNonNull(name), Objects.requireNonNull(type), Objects.requireNonNull(content));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public String location() {
        return LOCATION;
    }

    @Override
    public Reader reader() {
        return new StringReader(content);
    }

    @Override
    public Writer writer() {
        throw new UnsupportedOperationException("Write operation into '%s' not supported".formatted(this));
    }

    @Override
    public String readAsString() {
        // specialized implementation, to avoid calling #reader() and creating new Reader instance
        return content;
    }
}
