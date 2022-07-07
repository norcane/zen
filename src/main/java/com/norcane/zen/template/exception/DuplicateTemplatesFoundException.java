package com.norcane.zen.template.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.List;
import java.util.Objects;

public class DuplicateTemplatesFoundException extends ZenRuntimeException {
    private final String type;
    private final List<String> paths;

    public DuplicateTemplatesFoundException(String type, List<String> paths) {
        super();
        this.type = Objects.requireNonNull(type);
        this.paths = Objects.requireNonNull(paths);
    }

    @Override
    protected String problem() {
        return null;
    }

    @Override
    protected String solution() {
        return null;
    }

    @Override
    protected List<String> links() {
        return null;
    }
}
