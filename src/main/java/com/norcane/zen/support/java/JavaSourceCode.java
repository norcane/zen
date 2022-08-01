package com.norcane.zen.support.java;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.source.AbstractSourceCode;
import com.norcane.zen.source.syntax.CommentSyntax;

import java.util.Map;

public class JavaSourceCode extends AbstractSourceCode {

    protected JavaSourceCode(final CommentSyntax commentSyntax, final Resource resource) {
        super(commentSyntax, resource);
    }

    @Override
    public Map<String, Object> variables() {
        return Map.of();
    }
}
