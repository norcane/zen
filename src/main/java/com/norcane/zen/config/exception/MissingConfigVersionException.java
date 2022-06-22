package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

public class MissingConfigVersionException extends ZenRuntimeException {

    private final String source;

    public MissingConfigVersionException(String source) {
        super();
        this.source = source;
    }

    @Override
    public String toPretty() {
        return "Cannot parse configuration minCompatibleVersion from %s".formatted(source);
    }
}
