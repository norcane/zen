package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.List;
import java.util.stream.Collectors;

public class NoConfigFileFoundException extends ZenRuntimeException {

    private final List<String> possibleConfigFilePaths;

    public NoConfigFileFoundException(List<String> possibleConfigFilePaths) {
        super();
        this.possibleConfigFilePaths = possibleConfigFilePaths;
    }

    @Override
    public String toPretty() {
        final String paths = possibleConfigFilePaths
            .stream()
            .map("  - @|bold %s|@"::formatted)
            .collect(Collectors.joining("\n"));

        return """
            No configuration file found. Expected one of those configuration files:
                        
            %s
                        
            For more details, follow official documentation.
            """.formatted(paths);
    }
}
