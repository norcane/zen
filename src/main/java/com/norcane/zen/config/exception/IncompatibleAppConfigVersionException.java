package com.norcane.zen.config.exception;

import com.norcane.zen.exception.ZenRuntimeException;
import com.norcane.zen.meta.SemVer;

public class IncompatibleAppConfigVersionException extends ZenRuntimeException {

    public IncompatibleAppConfigVersionException(SemVer minimum, SemVer found) {
        super("Incompatible version of configuration (minimum required: %s, found: %s)".formatted(minimum.toPretty(), found.toPretty()));
    }


}
