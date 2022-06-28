package com.norcane.zen.resource.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResourceNotFoundException extends ZenRuntimeException {

    private final String location;

    public ResourceNotFoundException(String location) {
        super(location);
        this.location = Objects.requireNonNull(location);
    }

    @Override
    protected String problem() {
        return "Resource not found: %s".formatted(location);
    }

    @Override
    protected String solution() {
        return "Please check if given resource exists.";
    }

    @Override
    protected List<String> links() {
        return Collections.emptyList();
    }
}
