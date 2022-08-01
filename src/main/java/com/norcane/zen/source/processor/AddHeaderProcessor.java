package com.norcane.zen.source.processor;

import com.norcane.zen.source.SourceCode;
import com.norcane.zen.template.Template;
import com.norcane.zen.ui.alert.Alert;
import com.norcane.zen.ui.console.Console;

import java.util.Objects;

import picocli.CommandLine;

public class AddHeaderProcessor implements SourceProcessor {

    private final Console console;

    public AddHeaderProcessor(Console console) {
        this.console = Objects.requireNonNull(console);
    }

    @Override
    public String progressMessage(SourceCode sourceCode) {
        return "Adding header to @|bold %s|@".formatted(sourceCode.resource().location());
    }

    @Override
    public void process(SourceCode sourceCode, Template template) {
        // TODO implement
    }

    @Override
    public void printSummary() {
        console.render(Alert.info("TODO adding headers finished"));
    }

    @Override
    public int returnCode() {
        return CommandLine.ExitCode.OK;
    }
}
