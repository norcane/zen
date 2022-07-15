package com.norcane.zen.cli.command;

import com.norcane.zen.source.SourceProcessor;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import static org.mockito.Mockito.verify;

@QuarkusTest
class RunCommandTest {

    @InjectMock
    SourceProcessor sourceProcessor;

    @Inject
    RunCommand runCommand;

    @Test
    void execute() {
        runCommand.execute();

        // -- verify
        verify(sourceProcessor).process();
    }
}