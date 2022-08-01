package com.norcane.zen.cli.command;

import com.norcane.zen.resource.exception.ResourceNotFoundException;
import com.norcane.zen.ui.console.Console;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class AbstractCommandTest {

    @InjectMock
    Console console;

    @Test
    void run() {
        final TestCommand command = new TestCommand(console);

        command.call();
        assertTrue(command.executed);
    }

    @Test
    void printProductHeader() {
        final AbstractCommand command = new TestCommand(console);

        command.printProductHeader();
    }


    static class TestCommand extends AbstractCommand {

        boolean executed = false;

        public TestCommand(Console console) {
            super(console);
        }

        @Override
        protected int execute() {
            executed = true;
            throw new ResourceNotFoundException("testing exception handling");
        }
    }

}