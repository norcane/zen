package com.norcane.zen.exception;

import com.norcane.zen.meta.ProductInfo;

import java.util.List;

/**
 * Wrapper exception for some unexpected behaviour or state, with might be caused by bug.
 */
public class UnexpectedBehaviorException extends ZenRuntimeException {

    public UnexpectedBehaviorException(final String message) {
        super(message);
    }

    public UnexpectedBehaviorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    @Override
    public String problem() {
        return """
            Unexpected error occurred during application execution:
                        
            %s""".formatted(getCause());
    }

    @Override
    public String solution() {
        return "This might be application bug. Please report it using the link below.";
    }

    @Override
    public List<String> links() {
        return List.of(ProductInfo.linkReportBug());
    }
}
