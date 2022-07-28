package com.norcane.zen.ui.console;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import io.quarkus.arc.Unremovable;
import io.quarkus.runtime.LaunchMode;
import picocli.CommandLine;

/**
 * Implementation of {@link Console} using <a href="https://jline.github.io">jLine</a> as the terminal backend library.
 */
@ApplicationScoped
@Unremovable
public class JLineConsole implements Console {

    private Boolean cursorMovementSupported;
    private Terminal terminal;

    @PostConstruct
    void postConstruct() throws IOException {
        this.terminal = TerminalBuilder.terminal();
    }

    @PreDestroy
    void preDestroy() throws IOException {
        this.terminal.close();
    }

    @Override
    public void print(String text) {
        System.out.print(ansiString(text));
    }

    @Override
    public void printLn(String text) {
        System.out.println(ansiString(text));
    }

    @Override
    public void clearLine() {
        if (cursorMovementSupported()) {
            print("\u001b[1000D");
            print("\u001b[0K");
        }
    }

    @Override
    public boolean cursorMovementSupported() {
        if (this.cursorMovementSupported == null) {
            this.cursorMovementSupported = LaunchMode.current() == LaunchMode.NORMAL &&
                                           terminal.getStringCapability(InfoCmp.Capability.cursor_up) != null &&
                                           terminal.getStringCapability(InfoCmp.Capability.cursor_down) != null;
        }

        return this.cursorMovementSupported;
    }

    private String ansiString(final String text) {
        return CommandLine.Help.Ansi.AUTO.string(text);
    }
}
