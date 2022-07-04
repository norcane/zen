package com.norcane.zen.resource.filesystem;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.CannotReadResourceException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileSystemResource implements Resource {

    private final Path location;

    private FileSystemResource(final Path location) {
        this.location = location;
    }

    public static FileSystemResource of(final Path path) {
        return new FileSystemResource(Objects.requireNonNull(path));
    }

    @Override
    public String location() {
        return location.toString();
    }

    @Override
    public String readAsString() {
        try {
            return Files.readString(location);
        } catch (IOException e) {
            throw new CannotReadResourceException(this, e);
        }
    }

    @Override
    public String toString() {
        return "%s[%s]".formatted(getClass().getSimpleName(), location);
    }
}
