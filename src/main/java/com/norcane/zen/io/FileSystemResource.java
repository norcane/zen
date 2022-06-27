package com.norcane.zen.io;

import com.norcane.zen.io.exception.ResourceIOException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileSystemResource implements Resource {

    private final Path location;

    public FileSystemResource(final Path location) {
        this.location = Objects.requireNonNull(location);
    }

    @Override
    public boolean exists() {
        return Files.exists(this.location);
    }

    @Override
    public String getLocation() {
        return this.location.toString();
    }

    @Override
    public String readAsString() {
        try {
            return Files.readString(this.location);
        } catch (IOException e) {
            throw new ResourceIOException(this.location.toString(), e);
        }
    }
}
