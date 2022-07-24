package com.norcane.zen.cli.command;

import com.norcane.zen.exception.ZenRuntimeException;
import com.norcane.zen.meta.ProductInfo;
import com.norcane.zen.ui.console.Console;
import com.norcane.zen.ui.exception.ZenRuntimeExceptionPrinter;
import com.norcane.zen.ui.header.Header;

import io.quarkus.logging.Log;

/**
 * Base class to all CLI subcommands.
 */
public abstract class SubCommand implements Runnable {

    protected final Console console;

    public SubCommand(Console console) {
        this.console = console;
    }

    protected abstract void execute();

    @Override
    public void run() {
        printProductHeader();

        try {
            execute();
        } catch (ZenRuntimeException e) {
            handleZenRuntimeException(e);
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
        console.printLn("");
        console.render(Header.error(e.getMessage()));
        console.render(ZenRuntimeExceptionPrinter.of(e));
    }
}
