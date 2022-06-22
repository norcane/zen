package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.List;

public class MultipleConfigFilesFoundException extends ZenRuntimeException {

    public MultipleConfigFilesFoundException(List<String> foundConfigFilePaths) {
        super();
    }

    @Override
    public String toPretty() {
        return "Multiple config files found";
    }
}
