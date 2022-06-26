package com.norcane.zen.io;

import com.norcane.zen.io.exception.ResourceIOException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileSystemResource implements Resource {

    private final Path path;

    public FileSystemResource(final Path path) {
        this.path = Objects.requireNonNull(path);
    }

    @Override
    public boolean exists() {
        return Files.exists(this.path);
    }

    @Override
    public String getPath() {
        return this.path.toString();
    }

    @Override
    public String readAsString() {
        try {
            return Files.readString(this.path);
        } catch (IOException e) {
            throw new ResourceIOException(this.path.toString(), e);
        }
    }
}
