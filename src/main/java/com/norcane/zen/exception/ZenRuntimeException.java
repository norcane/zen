package com.norcane.zen.exception;

import com.norcane.zen.meta.ProductInfo;
import com.norcane.zen.ui.PrettyPrintable;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ZenRuntimeException extends RuntimeException implements PrettyPrintable {

    public ZenRuntimeException() {
        super();
    }

    public ZenRuntimeException(String message) {
        super(message);
    }

    public ZenRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZenRuntimeException(Throwable cause) {
        super(cause);
    }

    protected abstract String problem();

    protected abstract String solution();

    protected abstract List<String> links();

    @Override
    public String toPretty() {
        final List<String> providedLinks = links();
        final List<String> linksToDisplay = providedLinks.isEmpty() ? List.of(ProductInfo.WEBSITE) : providedLinks;
        final String listOfLinks = linksToDisplay
            .stream()
            .map("  - @|underline %s|@"::formatted)
            .collect(Collectors.joining("\n"));

        return
            """
                 
                @|bold,underline Problem:|@
                %s
                                
                @|bold,underline Possible Solution:|@
                %s
                                
                @|bold,underline See Also:|@
                %s
                """.formatted(problem(), solution(), listOfLinks);
    }
}
