package com.norcane.zen.resource.classpath;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.CannotReadResourceException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ClassPathResource implements Resource {

    private final String location;

    private ClassPathResource(final String location) {
        this.location = location;
    }

    public static ClassPathResource of(final String location) {
        return new ClassPathResource(Objects.requireNonNull(location));
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String readAsString() {
        try {
            try (final InputStream stream = getClass().getResourceAsStream(this.location)) {
                return new String(Objects.requireNonNull(stream).readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            throw new CannotReadResourceException(this, e);
        }
    }
}
