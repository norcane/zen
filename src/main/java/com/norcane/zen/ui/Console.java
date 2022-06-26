package com.norcane.zen.ui;

import javax.enterprise.context.ApplicationScoped;

import picocli.CommandLine;

@ApplicationScoped
public class Console {

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

    public void print(final PrettyPrintable printable) {
        print(printable.toPretty());
    }

    public void print(final String text) {
        System.out.println(ansiString(text));
    }

    private String ansiString(final String text) {
        return CommandLine.Help.Ansi.AUTO.string(text);
    }
}
