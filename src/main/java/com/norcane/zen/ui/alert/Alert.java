package com.norcane.zen.ui.alert;

import com.norcane.zen.ui.UIComponent;

public interface Alert extends UIComponent {

    private static String header(final char icon, final String color, final String label, final String message) {
        return "@|bold,bg(%s),white [%s] %s:|@ @|bold,%s %s|@".formatted(color, icon, label, color, message);
    }

    static Alert info(final String message) {
        return console -> console.printLn(header('i', "blue", "INFO", message));
    }

    static Alert error(final String message) {
        return console -> console.printLn(header('!', "red", "ERROR", message));
    }
}
