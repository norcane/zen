package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

public class ConfigParseException extends ZenRuntimeException {
    private final String source;
    private final Throwable cause;

    public ConfigParseException(String source, Throwable cause) {
        super("Error loading configuration %s".formatted(source), cause);
        this.source = source;
        this.cause = cause;
    }

    @Override
    public String toPretty() {
        return "Error loading configuration %s: %s".formatted(source, cause.getMessage());
    }
}
