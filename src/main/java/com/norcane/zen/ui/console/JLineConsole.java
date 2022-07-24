package com.norcane.zen.ui.console;

import com.norcane.zen.exception.UnexpectedBehaviorException;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import io.quarkus.logging.Log;
import picocli.CommandLine;

/**
 * Implementation of {@link Console} using <a href="https://jline.github.io">jLine</a> as the terminal backend library.
 */
@ApplicationScoped
public class JLineConsole implements Console {

    private final Terminal terminal;

    public JLineConsole() {
        try {
            this.terminal = TerminalBuilder.terminal();
        } catch (IOException e) {
            throw new UnexpectedBehaviorException("Error initializing jLine terminal", e);
        }
    }

    @PreDestroy
    void destroy() {
        try {
            this.terminal.close();
        } catch (IOException e) {
            // do not eventually propagate the exception further during application shutdown
            Log.error(e);
        }
    }

    @Override
    public void print(String text) {
        System.out.print(ansiString(text));
    }

    @Override
    public void printLn(String text) {
        System.out.println(ansiString(text));
    }

    private String ansiString(final String text) {
        return CommandLine.Help.Ansi.AUTO.string(text);
    }
}
