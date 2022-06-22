package com.norcane.zen.exception;

import com.norcane.zen.ui.PrettyPrintable;

public abstract class ZenRuntimeException extends RuntimeException implements PrettyPrintable {

    public ZenRuntimeException() {
        super();
    }

    public ZenRuntimeException(String message) {
        super(message);
    }

    public ZenRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZenRuntimeException(Throwable cause) {
        super(cause);
    }

    public ZenRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
