package com.norcane.zen.cli.command;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RunCommandTest {

    @Inject
    RunCommand runCommand;

    @Test
    void execute() {
        runCommand.execute();
    }
}