package com.norcane.zen.cli.command;

import com.norcane.zen.config.AppConfigManager;
import com.norcane.zen.config.model.AppConfig;
import com.norcane.zen.source.SourceCode;
import com.norcane.zen.source.SourceCodeManager;
import com.norcane.zen.source.processor.AddHeaderProcessor;
import com.norcane.zen.source.processor.SourceProcessor;
import com.norcane.zen.template.TemplateManager;
import com.norcane.zen.ui.console.Console;
import com.norcane.zen.ui.progressbar.ProgressBar;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import picocli.CommandLine;

@CommandLine.Command(name = "run", description = "adds copyright headers")
public class RunCommand extends AbstractCommand {

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
    protected int execute() {
        printSummaryIntro();

        final AppConfig appConfig = appConfigManager.finalConfigRef().config();
        final List<SourceCode> sourceCodes = sourceCodeManager.sourceCodes();

        final SourceProcessor processor = switch (appConfig.mode()) {
            case ADD -> new AddHeaderProcessor(console);
            case DROP -> throw new UnsupportedOperationException("not implemented yet");
            case REPLACE -> throw new UnsupportedOperationException("not implemented yet");
        };

        for (final SourceCode sourceCode : ProgressBar.wrap(sourceCodes, processor::progressMessage, console)) {
            // TODO implement
        }

        console.emptyLine();
        processor.printSummary();
        return processor.returnCode();
    }


    private void printSummaryIntro() {
        final int numberOfTemplates = templateManager.templates().size();
        final int numberOfSources = sourceCodeManager.sourceCodes().size();

        console.printLn("Loaded configuration from @|bold %s|@".formatted(appConfigManager.finalConfigRef().resource().location()));
        console.printLn("Loaded @|bold %d|@ templates from @|bold %s|@".formatted(numberOfTemplates, templateManager.templatePaths()));
        console.printLn("Loaded @|bold %d|@ source code files from @|bold %s|@".formatted(numberOfSources, sourceCodeManager.sourceCodePaths()));

    }
}
