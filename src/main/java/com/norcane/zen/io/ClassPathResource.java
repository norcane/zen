package com.norcane.zen.io;

import com.norcane.zen.io.exception.ResourceIOException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ClassPathResource implements Resource {

    private final String path;

    public ClassPathResource(final String path) {
        this.path = Objects.requireNonNull(path);
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
            return Files.readString(Paths.get(Objects.requireNonNull(this.getClass().getResource(this.path)).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new ResourceIOException(getPath(), e);
        }
    }
}
