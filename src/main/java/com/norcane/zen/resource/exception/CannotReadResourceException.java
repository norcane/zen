package com.norcane.zen.resource.exception;

import com.norcane.zen.exception.ZenRuntimeException;
import com.norcane.zen.resource.Resource;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CannotReadResourceException extends ZenRuntimeException {

    private final Resource resource;

    public CannotReadResourceException(Resource resource) {
        this.resource = Objects.requireNonNull(resource);
    }

    public CannotReadResourceException(Resource resource, Throwable cause) {
        super(cause);
        this.resource = Objects.requireNonNull(resource);
    }

    @Override
    protected String problem() {
        return "Cannot read resource %s".formatted(resource.location());
    }

    @Override
    protected String solution() {
        return """
            Very likely the given resource exists, but either might be a directory instead of a file,\
            or the file isn't readable (missing privileges). Please check if the given path is correct.""";
    }

    @Override
    protected List<String> links() {
        return Collections.emptyList();
    }
}
