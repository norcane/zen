package com.norcane.zen.cli.command;

import com.norcane.zen.meta.ProductInfo;

import picocli.CommandLine;

public abstract class SubCommand implements Runnable {

    protected void printProductHeader() {
        System.out.println(CommandLine.Help.Ansi.AUTO.string(ProductInfo.productHeader()));
    }
}
