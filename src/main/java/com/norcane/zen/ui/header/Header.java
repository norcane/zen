package com.norcane.zen.ui.header;

import com.norcane.zen.ui.UIComponent;

public interface Header extends UIComponent {

    private static String header(final char icon, final String color, final String label, final String message) {
        return "@|bold,bg(%s),white [%s] %s:|@ @|bold,%s %s|@".formatted(color, icon, label, color, message);
    }

    static Header info(final String message) {
        return console -> console.printLn(header('i', "blue", "INFO", message));
    }

    static Header error(final String message) {
        return console -> console.printLn(header('!', "red", "ERROR", message));
    }
}
