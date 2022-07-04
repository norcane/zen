package com.norcane.zen.resource.inline;

import com.norcane.zen.resource.Resource;

import java.util.Objects;

public class InlineResource implements Resource {

    private static final String LOCATION = "<inline string>";

    private final String content;

    private InlineResource(final String content) {
        this.content = content;
    }

    public static InlineResource of(final String content) {
        return new InlineResource(Objects.requireNonNull(content));
    }

    @Override
    public String location() {
        return LOCATION;
    }

    @Override
    public String readAsString() {
        return content;
    }
}
