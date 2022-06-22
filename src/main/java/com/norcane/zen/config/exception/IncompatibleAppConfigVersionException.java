package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;
import com.norcane.zen.meta.SemVer;

public class IncompatibleAppConfigVersionException extends ZenRuntimeException {

    private final SemVer minimum;
    private final SemVer found;

    public IncompatibleAppConfigVersionException(SemVer minimum, SemVer found) {
        super();
        this.minimum = minimum;
        this.found = found;
    }


    @Override
    public String toPretty() {
        return "Incompatible version of configuration (minimum required: %s, found: %s)".formatted(minimum.toPretty(), found.toPretty());
    }
}
