package com.norcane.zen.support.java;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.source.AbstractSourceCode;
import com.norcane.zen.source.headersyntax.HeaderSyntax;

import java.util.Map;

public class JavaSourceCode extends AbstractSourceCode {

    protected JavaSourceCode(final HeaderSyntax headerSyntax, final Resource resource) {
        super(headerSyntax, resource);
    }

    @Override
    public Map<String, Object> variables() {
        return Map.of();
    }
}
