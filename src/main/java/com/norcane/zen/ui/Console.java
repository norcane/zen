package com.norcane.zen.ui;

import javax.enterprise.context.ApplicationScoped;

import picocli.CommandLine;

@ApplicationScoped
public class Console {

    public void emptyLine() {
        print("");
    }

    public void error(PrettyPrintable printable) {
        error(printable.toPretty());
    }

    public void error(String text) {
        message("ERROR", text, "red");
    }

    public void message(String heading, String text, String bgColor) {
        print("@|bold,bg(%s),white [X] %s:|@".formatted(bgColor, heading));
        print(text);
    }

    public void print(PrettyPrintable printable) {
        print(printable.toPretty());
    }

    public void print(String text) {
        System.out.println(ansiString(text));
    }

    private String ansiString(String text) {
        return CommandLine.Help.Ansi.AUTO.string(text);
    }
}
