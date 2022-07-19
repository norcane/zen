package com.norcane.zen.cli.command;

import com.norcane.zen.source.SourceCodeManager;
import com.norcane.zen.template.TemplateManager;
import com.norcane.zen.ui.Console;

import java.util.Objects;

import javax.inject.Inject;

import picocli.CommandLine;

@CommandLine.Command(name = "run", description = "adds copyright headers")
public class RunCommand extends SubCommand {

    private final SourceCodeManager sourceCodeManager;
    private final TemplateManager templateManager;

    @Inject
    public RunCommand(final Console console,
                      final SourceCodeManager sourceCodeManager,
                      final TemplateManager templateManager) {

        super(Objects.requireNonNull(console));
        this.sourceCodeManager = Objects.requireNonNull(sourceCodeManager);
        this.templateManager = Objects.requireNonNull(templateManager);
    }

    @Override
    public void execute() {
        // print initial info
        console.print(
            "Loaded @|bold %d|@ templates from @|bold %s|@".formatted(templateManager.templates().size(), templateManager.templatePaths()));
        console.print(
            "Loaded @|bold %d|@ source code files from @|bold %s|@".formatted(sourceCodeManager.sourceCodes().size(), sourceCodeManager.sourceCodePaths()));
    }
}
