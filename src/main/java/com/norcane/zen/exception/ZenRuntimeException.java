package com.norcane.zen.exception;

import java.util.List;

public abstract class ZenRuntimeException extends RuntimeException {

    public ZenRuntimeException(String message) {
        super(message);
    }

    public ZenRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZenRuntimeException(Throwable cause) {
        super(cause);
    }

    public abstract String problem();

    public abstract String solution();

    public abstract List<String> links();
}
