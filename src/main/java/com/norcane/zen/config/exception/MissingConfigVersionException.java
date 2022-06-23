package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.Collections;
import java.util.List;

public class MissingConfigVersionException extends ZenRuntimeException {

    private final String source;

    public MissingConfigVersionException(String source) {
        super("Cannot parse configuration version from %s".formatted(source));
        this.source = source;
    }

    @Override
    protected String problem() {
        return "Cannot parse configuration version from %s".formatted(source);
    }

    @Override
    protected String solution() {
        return "Please make sure that the info about minimum compatible version is present in the configuration file.";
    }

    @Override
    protected List<String> links() {
        return Collections.emptyList();
    }
}
