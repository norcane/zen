package com.norcane.zen.cli.command;

import com.norcane.zen.exception.ZenRuntimeException;
import com.norcane.zen.meta.ProductInfo;
import com.norcane.zen.ui.alert.Alert;
import com.norcane.zen.ui.console.Console;
import com.norcane.zen.ui.exception.ZenRuntimeExceptionPrinter;

import java.util.concurrent.Callable;

import io.quarkus.logging.Log;
import picocli.CommandLine;

/**
 * Base class to all CLI subcommands.
 */
public abstract class AbstractCommand implements Callable<Integer> {

    protected final Console console;

    public AbstractCommand(Console console) {
        this.console = console;
    }

    protected abstract int execute();

    @Override
    public Integer call() {
        printProductHeader();

        try {
            return execute();
        } catch (ZenRuntimeException e) {
            handleZenRuntimeException(e);
            return CommandLine.ExitCode.SOFTWARE;
        }
    }

    /**
     * Prints product header info.
     */
    protected void printProductHeader() {
        console.printLn(ProductInfo.productHeader());
    }

    void handleZenRuntimeException(ZenRuntimeException e) {
        Log.error(e);
        console.emptyLine();
        console.render(Alert.error(e.getMessage()));
        console.render(ZenRuntimeExceptionPrinter.of(e));
    }
}
