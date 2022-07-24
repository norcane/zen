package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.Collections;
import java.util.List;

public class ConfigParseException extends ZenRuntimeException {
    private final String source;
    private final Throwable cause;

    public ConfigParseException(String source, Throwable cause) {
        super("Error loading configuration file %s: %s".formatted(source, cause.getMessage()), cause);
        this.source = source;
        this.cause = cause;
    }

    @Override
    public String problem() {
        return "Error loading configuration file %s: %s".formatted(source, cause.getMessage());
    }

    @Override
    public String solution() {
        return """
            Please check that some of the following isn't wrong:
                        
              - syntax of the configuration file is invalid
              - you don't have enough right to access the configuration file
            """;
    }

    @Override
    public List<String> links() {
        return Collections.emptyList();
    }
}
