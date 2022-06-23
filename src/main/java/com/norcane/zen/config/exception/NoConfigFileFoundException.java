package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NoConfigFileFoundException extends ZenRuntimeException {

    private final List<String> possibleConfigFilePaths;

    public NoConfigFileFoundException(List<String> possibleConfigFilePaths) {
        super();
        this.possibleConfigFilePaths = possibleConfigFilePaths;
    }

    @Override
    protected String problem() {
        return "No valid configuration file found.";
    }

    @Override
    protected String solution() {
        final String listOfPaths = possibleConfigFilePaths
            .stream()
            .map("  - @|bold %s|@"::formatted)
            .collect(Collectors.joining("\n"));

        return """
            One of the following configuration files is expected to exist:
                        
            %s
                        
            Please create one.
            """.strip().formatted(listOfPaths);
    }

    @Override
    protected List<String> links() {
        return Collections.emptyList();
    }
}
