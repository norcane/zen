package com.norcane.zen.cli.command;

import com.norcane.zen.resource.exception.ResourceNotFoundException;
import com.norcane.zen.ui.Console;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class SubCommandTest {

    @InjectMock
    Console console;

    @Test
    void run() {
        final TestSubCommand command = new TestSubCommand(console);

        command.run();
        assertTrue(command.executed);
    }

    @Test
    void printProductHeader() {
        final SubCommand command = new TestSubCommand(console);

        command.printProductHeader();
    }


    static class TestSubCommand extends SubCommand {

        boolean executed = false;

        public TestSubCommand(Console console) {
            super(console);
        }

        @Override
        protected void execute() {
            executed = true;
            throw new ResourceNotFoundException("testing exception handling");
        }
    }

}