package com.norcane.zen.cli.command;

import com.norcane.zen.source.SourceProcessor;
import com.norcane.zen.ui.Console;

import javax.inject.Inject;

import picocli.CommandLine;

@CommandLine.Command(
    name = "run",
    description = "adds copyright headers"
)
public class RunCommand extends SubCommand {

    private final SourceProcessor sourceProcessor;

    @Inject
    public RunCommand(Console console,
                      SourceProcessor sourceProcessor) {

        super(console);
        this.sourceProcessor = sourceProcessor;
    }

    @Override
    public void execute() {
        sourceProcessor.process();
    }
}
