package com.norcane.zen.ui;

import com.norcane.zen.exception.UnexpectedBehaviorException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import picocli.CommandLine;

@ApplicationScoped
public class Console {

    final boolean enabled;

    private final Terminal terminal;

    public Console(@ConfigProperty(name = "zen.console.enabled", defaultValue = "true") final boolean enabled) {
        this.enabled = enabled;

        try {
            terminal = TerminalBuilder.terminal();
        } catch (IOException e) {
            throw new UnexpectedBehaviorException("Error creating Terminal instance", e);
        }
    }

    @PreDestroy
    void destroy() {
        if (terminal != null) {
            try {
                terminal.close();
            } catch (IOException e) {
                throw new UnexpectedBehaviorException("Error closing Terminal instance", e);
            }
        }
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

    public int width() {
        return terminal.getWidth();
    }

    String ansiString(final String text) {
        return CommandLine.Help.Ansi.AUTO.string(text);
    }
}
