package com.norcane.zen.io.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.Collections;
import java.util.List;

public class ResourceIOException extends ZenRuntimeException {
    private final String path;

    public ResourceIOException(final String path) {
        super();
        this.path = path;
    }

    public ResourceIOException(final String path, final Throwable cause) {
        super(cause);
        this.path = path;
    }

    @Override
    protected String problem() {
        return "Error reading resource from path @|bold %s|@".formatted(getCause().getMessage());
    }

    @Override
    protected String solution() {
        return """
            Please verify that:
              - such resource exists at given path
              - you have enough rights to access it""";
    }

    @Override
    protected List<String> links() {
        return Collections.emptyList();
    }
}
