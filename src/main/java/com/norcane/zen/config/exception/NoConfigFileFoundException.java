package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.List;

public class NoConfigFileFoundException extends ZenRuntimeException {

    public NoConfigFileFoundException(List<String> possibleConfigFilePaths) {
        super();
    }

    @Override
    public String toPretty() {
        return "No configuration file found";
    }
}
