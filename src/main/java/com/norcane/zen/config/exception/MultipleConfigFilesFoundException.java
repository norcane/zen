package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleConfigFilesFoundException extends ZenRuntimeException {

    private final List<String> foundConfigFilePaths;

    public MultipleConfigFilesFoundException(List<String> foundConfigFilePaths) {
        super("Multiple configuration files found");
        this.foundConfigFilePaths = foundConfigFilePaths;
    }

    @Override
    public String problem() {
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
    public String solution() {
        return "Please make sure that only one configuration file exists.";
    }

    @Override
    public List<String> links() {
        return Collections.emptyList();
    }
}
