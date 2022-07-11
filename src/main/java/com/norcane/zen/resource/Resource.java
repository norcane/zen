package com.norcane.zen.resource;

import com.norcane.zen.resource.exception.CannotReadResourceException;
import com.norcane.zen.resource.inline.InlineResource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public interface Resource {

    String name();

    String type();

    String location();

    InputStream inputStream();

    default String readAsString() {
        try (final InputStream stream = inputStream()) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CannotReadResourceException(this, e);
        }
    }

    static Resource inline(final String name, final String type, final String content) {
        return InlineResource.of(name, type, content);
    }
}
