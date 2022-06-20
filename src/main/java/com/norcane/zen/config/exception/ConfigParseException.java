package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

public class ConfigParseException extends ZenRuntimeException {

    public ConfigParseException(String source, Throwable cause) {
        super("Error loading configuration %s: %s".formatted(source, cause.getMessage()));
    }
}
