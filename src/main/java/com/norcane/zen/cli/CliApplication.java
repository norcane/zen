package com.norcane.zen.cli;

import com.norcane.zen.meta.ProductInfo;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(
    name = "headroom",
    mixinStandardHelpOptions = true,
    description = "Manager for copyright/license headers")
public class CliApplication {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @CommandLine.Command(
        name = "run",
        description = "adds copyright headers",
        header = ProductInfo.PRODUCT_HEADER)
    public void runCommand() {
        System.out.println("RUN command");
    }
}
