package com.norcane.zen.cli.command;

import com.norcane.zen.meta.ProductInfo;

import picocli.CommandLine;

/**
 * Base class to all CLI subcommands.
 */
public abstract class SubCommand implements Runnable {

    /**
     * Prints product header into command line.
     */
    protected void printProductHeader() {
        System.out.println(CommandLine.Help.Ansi.AUTO.string(ProductInfo.productHeader()));
    }
}
