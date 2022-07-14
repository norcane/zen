package com.norcane.zen.resource.classpath;

import com.google.common.io.Files;

import com.norcane.zen.resource.Resource;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.Objects;

public class ClassPathResource implements Resource {

    private final String location;
    private final String name;
    private final String type;

    private ClassPathResource(final String location) {
        this.location = location;
        this.name = Files.getNameWithoutExtension(location);
        this.type = Files.getFileExtension(location);
    }

    public static ClassPathResource of(final String location) {
        return new ClassPathResource(Objects.requireNonNull(location));
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
        return location;
    }

    @Override
    public Reader reader() {
        return new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(this.location)));
    }

    @Override
    public Writer writer() {
        throw new UnsupportedOperationException("Write operation into '%s' not supported".formatted(this));
    }
}
