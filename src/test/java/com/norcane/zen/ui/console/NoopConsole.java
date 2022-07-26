package com.norcane.zen.ui.console;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;


@Alternative
@Priority(1)
@ApplicationScoped
public class NoopConsole implements Console {

    @Override
    public void print(String text) {
        // do nothing
    }

    @Override
    public void printLn(String text) {
        // do nothing
    }

    @Override
    public void clearLine() {
        // do nothing
    }

    @Override
    public boolean cursorMovementSupported() {
        return false;
    }
}
