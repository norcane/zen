package com.norcane.zen.cli.command;

import picocli.CommandLine;

@CommandLine.Command(
    name = "run",
    description = "adds copyright headers"
)
public class RunCommand extends SubCommand {

    @Override
    public void run() {
        printProductHeader();

        System.out.println("RUN command here");
    }
}
