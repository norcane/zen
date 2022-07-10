package com.norcane.zen.resource.classpath;

import com.google.common.io.Files;

import com.norcane.zen.resource.Resource;

import java.io.InputStream;
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
    public InputStream inputStream() {
        return getClass().getResourceAsStream(this.location);
    }
}
