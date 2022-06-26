package com.norcane.zen.io;

import com.norcane.zen.io.exception.ResourceIOException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class ClassPathResource implements Resource {

    public static final String PREFIX = "classpath:";

    private final String path;

    private ClassPathResource(final String path) {
        this.path = path;
    }

    public static ClassPathResource of(final String path) {
        Objects.requireNonNull(path);
        return new ClassPathResource(path.startsWith(PREFIX) ? path.substring(PREFIX.length()) : path);
    }

    @Override
    public boolean exists() {
        return this.getClass().getResource(this.path) != null;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String readAsString() {
        try {
            try (final InputStream stream = getClass().getResourceAsStream(this.path)) {
                return new String(Objects.requireNonNull(stream).readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new ResourceIOException(getPath(), e);
        }
    }

    @Override
    public String toString() {
        return PREFIX + this.path;
    }
}
