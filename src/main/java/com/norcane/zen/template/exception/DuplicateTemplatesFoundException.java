package com.norcane.zen.template.exception;

import com.norcane.zen.exception.ZenRuntimeException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DuplicateTemplatesFoundException extends ZenRuntimeException {
    private final String type;
    private final List<String> paths;

    public DuplicateTemplatesFoundException(String type, List<String> paths) {
        super("Multiple templates found for source file type '%s'".formatted(type));
        this.type = Objects.requireNonNull(type);
        this.paths = Objects.requireNonNull(paths);
    }

    @Override
    public String problem() {
        final String listOfPaths = paths.stream()
            .map("  - @|bold s|@"::formatted)
            .collect(Collectors.joining("\n"));

        return """
            Template paths contain multiple templates for same source file type @|bold %s|@:
            %s
            """.strip().formatted(type, listOfPaths);
    }

    @Override
    public String solution() {
        return "Make sure that only one template is present for selected file type";
    }

    @Override
    public List<String> links() {
        return List.of();
    }
}
