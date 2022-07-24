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

    public ResourceNotFoundException(String location, Throwable cause) {
        super(location, cause);
        this.location = Objects.requireNonNull(location);
    }

    @Override
    public String problem() {
        return "Resource not found: %s".formatted(location);
    }

    @Override
    public String solution() {
        return "Please check if given resource exists.";
    }

    @Override
    public List<String> links() {
        return Collections.emptyList();
    }
}
