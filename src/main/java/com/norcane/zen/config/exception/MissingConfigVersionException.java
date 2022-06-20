package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

public class MissingConfigVersionException extends ZenRuntimeException {

    public MissingConfigVersionException(String source) {
        super("Cannot parse configuration version from %s".formatted(source));
    }
}
