package com.norcane.zen.ui.exception;

import com.norcane.zen.exception.UnexpectedBehaviorException;
import com.norcane.zen.exception.ZenRuntimeException;
import com.norcane.zen.ui.console.Console;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ZenRuntimeExceptionPrinterTest {

    @Inject
    Console console;

    @Test
    void render() {
        final ZenRuntimeException exception = new UnexpectedBehaviorException("Uh oh!", new IOException());
        console.render(ZenRuntimeExceptionPrinter.of(exception));
    }
}