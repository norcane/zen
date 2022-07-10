package com.norcane.zen.resource.inline;

import com.norcane.zen.resource.Resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class InlineResource implements Resource {

    private static final String LOCATION = "<inline string>";

    private final String name;
    private final String type;
    private final String content;

    private InlineResource(String name, String type, final String content) {
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
    public InputStream inputStream() {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String readAsString() {
        return content;
    }
}
