package com.norcane.zen.resource.filesystem;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.resource.exception.CannotReadResourceException;
import com.norcane.zen.resource.exception.CannotWriteResourceException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileSystemResource implements Resource {

    private final Path location;
    private final String name;
    private final String type;

    private FileSystemResource(final Path location) {
        this.location = location;
        this.name = com.google.common.io.Files.getNameWithoutExtension(location.toString());
        this.type = com.google.common.io.Files.getFileExtension(location.toString());

    }

    public static FileSystemResource of(final Path path) {
        return new FileSystemResource(Objects.requireNonNull(path));
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
        return location.toString();
    }

    @Override
    public Reader reader() {
        try {
            return Files.newBufferedReader(location);
        } catch (IOException e) {
            throw new CannotReadResourceException(this, e);
        }
    }

    @Override
    public Writer writer() {
        try {
            return Files.newBufferedWriter(location);
        } catch (Exception e) {
            throw new CannotWriteResourceException(this, e);
        }
    }

    @Override
    public String toString() {
        return "%s[%s]".formatted(getClass().getSimpleName(), location);
    }
}
