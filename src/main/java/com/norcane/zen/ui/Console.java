package com.norcane.zen.ui;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

import picocli.CommandLine;

@ApplicationScoped
public class Console {

    final boolean enabled;

    public Console(@ConfigProperty(name = "zen.console.enabled", defaultValue = "true") final boolean enabled) {
        this.enabled = enabled;
    }

    public void emptyLine() {
        print("");
    }

    public void error(final PrettyPrintable printable) {
        error(printable.toPretty());
    }

    public void error(final String text) {
        message("ERROR", text, "red");
    }

    public void message(final String heading, final String text, final String bgColor) {
        print("@|bold,bg(%s),white [X] %s:|@".formatted(bgColor, heading));
        print(text);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void print(final PrettyPrintable printable) {
        print(printable.toPretty());
    }

    public void print(final String text) {
        if (isEnabled()) {
            System.out.println(ansiString(text));
        }
    }

    String ansiString(final String text) {
        return CommandLine.Help.Ansi.AUTO.string(text);
    }
}
