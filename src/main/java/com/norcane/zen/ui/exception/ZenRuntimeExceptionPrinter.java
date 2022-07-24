package com.norcane.zen.ui.exception;

import com.norcane.zen.exception.ZenRuntimeException;
import com.norcane.zen.meta.ProductInfo;
import com.norcane.zen.ui.UIComponent;
import com.norcane.zen.ui.console.Console;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@link UIComponent} that prints given {@link ZenRuntimeException} in user-friendly manner.
 */
public class ZenRuntimeExceptionPrinter implements UIComponent {

    private final ZenRuntimeException exception;

    private ZenRuntimeExceptionPrinter(final ZenRuntimeException exception) {
        this.exception = exception;
    }

    public static ZenRuntimeExceptionPrinter of(final ZenRuntimeException exception) {
        return new ZenRuntimeExceptionPrinter(Objects.requireNonNull(exception));
    }

    @Override
    public void render(final Console console) {
        final List<String> providedLinks = exception.links();
        final List<String> linksToDisplay = providedLinks.isEmpty() ? List.of(ProductInfo.WEBSITE) : providedLinks;
        final String listOfLinks = linksToDisplay
            .stream()
            .map("  - @|underline %s|@"::formatted)
            .collect(Collectors.joining("\n"));

        final String rendered =
            """
                 
                @|bold,underline Problem:|@
                %s
                                
                @|bold,underline Possible Solution:|@
                %s
                                
                @|bold,underline See Also:|@
                %s
                """.formatted(exception.problem(), exception.solution(), listOfLinks);

        console.printLn(rendered);
    }
}
