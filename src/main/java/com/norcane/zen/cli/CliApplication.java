package com.norcane.zen.cli;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(
    name = "headroom",
    mixinStandardHelpOptions = true,
    description = "Manager for copyright/license headers")
public class CliApplication {

    @CommandLine.Command(name = "run", description = "adds copyright headers")
    public void runCommand() {
        System.out.println("RUN command");
    }
}
