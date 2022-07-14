package com.norcane.zen.resource;

import com.google.common.io.CharStreams;

import com.norcane.zen.resource.classpath.ClassPathResource;
import com.norcane.zen.resource.exception.CannotReadResourceException;
import com.norcane.zen.resource.filesystem.FileSystemResource;
import com.norcane.zen.resource.inline.InlineResource;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;

public interface Resource {

    String name();

    String type();

    String location();

    Reader reader();

    Writer writer();

    default String readAsString() {
        try (final Reader stream = reader()) {
            return CharStreams.toString(stream);
        } catch (Exception e) {
            throw new CannotReadResourceException(this, e);
        }
    }

    static Resource file(final String location) {
        return Resource.file(Path.of(location));
    }

    static Resource file(Path path) {
        return FileSystemResource.of(path);
    }

    static Resource inline(final String name, final String type, final String content) {
        return InlineResource.of(name, type, content);
    }

    static Resource classPath(final String location) {
        return ClassPathResource.of(location);
    }
}
