package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleConfigFilesFoundException extends ZenRuntimeException {

    private final List<String> foundConfigFilePaths;

    public MultipleConfigFilesFoundException(List<String> foundConfigFilePaths) {
        super();
        this.foundConfigFilePaths = foundConfigFilePaths;
    }

    @Override
    protected String problem() {
        final String listOfPaths = foundConfigFilePaths
            .stream()
            .map("  - @|bold %s|@"::formatted)
            .collect(Collectors.joining("\n"));

        return """
            Multiple configuration files found:
                        
            %s
            """.strip().formatted(listOfPaths);
    }

    @Override
    protected String solution() {
        return "Please make sure that only one configuration file exists.";
    }

    @Override
    protected List<String> links() {
        return Collections.emptyList();
    }
}
