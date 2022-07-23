package com.norcane.zen.cli.command;

import com.norcane.zen.config.AppConfigManager;
import com.norcane.zen.source.SourceCodeManager;
import com.norcane.zen.template.TemplateManager;
import com.norcane.zen.ui.Console;

import java.util.Objects;

import javax.inject.Inject;

import picocli.CommandLine;

@CommandLine.Command(name = "run", description = "adds copyright headers")
public class RunCommand extends SubCommand {

    private final AppConfigManager appConfigManager;
    private final SourceCodeManager sourceCodeManager;
    private final TemplateManager templateManager;

    @Inject
    public RunCommand(final AppConfigManager appConfigManager,
                      final Console console,
                      final SourceCodeManager sourceCodeManager,
                      final TemplateManager templateManager) {

        super(Objects.requireNonNull(console));
        this.appConfigManager = Objects.requireNonNull(appConfigManager);
        this.sourceCodeManager = Objects.requireNonNull(sourceCodeManager);
        this.templateManager = Objects.requireNonNull(templateManager);
    }

    @Override
    protected void execute() {
        printSummaryIntro();
        System.out.println("WIDTH: " + console.width());
    }

    private void printSummaryIntro() {
        final int numberOfTemplates = templateManager.templates().size();
        final int numberOfSources = sourceCodeManager.sourceCodes().size();

        console.print("Loaded configuration from @|bold %s|@".formatted(appConfigManager.finalConfigRef().resource().location()));
        console.print("Loaded @|bold %d|@ templates from @|bold %s|@".formatted(numberOfTemplates, templateManager.templatePaths()));
        console.print("Loaded @|bold %d|@ source code files from @|bold %s|@".formatted(numberOfSources, sourceCodeManager.sourceCodePaths()));

    }
}
