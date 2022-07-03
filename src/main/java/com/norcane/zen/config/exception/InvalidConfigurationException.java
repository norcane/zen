package com.norcane.zen.config.exception;

import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.exception.ZenRuntimeException;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

public class InvalidConfigurationException extends ZenRuntimeException {

    private final Set<ConstraintViolation<AppConfig>> violations;

    public InvalidConfigurationException(Set<ConstraintViolation<AppConfig>> violations) {
        super(violations.toString());
        this.violations = violations;
    }

    @Override
    protected String problem() {
        return """
            Provided application config source is invalid and has following issues:
                        
            %s
            """.strip().formatted(listOfViolations());
    }

    @Override
    protected String solution() {
        return "Please check the error messages above and correct the configuration appropriately.";
    }

    @Override
    protected List<String> links() {
        return Collections.emptyList();
    }

    private String listOfViolations() {
        return violations
            .stream()
            .map(violation -> "  - @|bold,underline %s|@ @|bold %s|@".formatted(violation.getPropertyPath(), violation.getMessage()))
            .collect(Collectors.joining("\n"));
    }
}
