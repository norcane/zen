package com.norcane.zen.resource.exception;

import com.norcane.zen.exception.ZenRuntimeException;
import com.norcane.zen.resource.Resource;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CannotWriteResourceException extends ZenRuntimeException {

    private final Resource resource;

    public CannotWriteResourceException(Resource resource, Throwable cause) {
        super(cause);
        this.resource = Objects.requireNonNull(resource);
    }

    @Override
    public String problem() {
        return "Cannot write resource %s".formatted(resource.location());
    }

    @Override
    public String solution() {
        return "Please check if target resource exists and you have write privileges.";
    }

    @Override
    public List<String> links() {
        return Collections.emptyList();
    }
}
