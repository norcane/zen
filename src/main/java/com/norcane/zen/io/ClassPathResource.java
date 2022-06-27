package com.norcane.zen.io;

import com.norcane.zen.io.exception.ResourceIOException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class ClassPathResource implements Resource {

    public static final String PREFIX = "classpath:";

    private final String location;

    private ClassPathResource(final String location) {
        this.location = location;
    }

    public static ClassPathResource of(final String location) {
        Objects.requireNonNull(location);
        return new ClassPathResource(location.startsWith(PREFIX) ? location.substring(PREFIX.length()) : location);
    }

    @Override
    public boolean exists() {
        return this.getClass().getResource(this.location) != null;
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public String readAsString() {
        try {
            try (final InputStream stream = getClass().getResourceAsStream(this.location)) {
                return new String(Objects.requireNonNull(stream).readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new ResourceIOException(getLocation(), e);
        }
    }

    @Override
    public String toString() {
        return PREFIX + this.location;
    }
}
