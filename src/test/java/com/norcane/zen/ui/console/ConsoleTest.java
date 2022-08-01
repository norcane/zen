package com.norcane.zen.ui.console;

import com.norcane.zen.ui.UIComponent;
import com.norcane.zen.ui.alert.Alert;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ConsoleTest {

    @Inject
    Console console;

    @Test
    void render() {
        final UIComponent component = Alert.info("Hello, world!");
        console.render(component);
    }
}