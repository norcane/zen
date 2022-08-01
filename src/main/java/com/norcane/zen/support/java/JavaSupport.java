package com.norcane.zen.support.java;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.source.SourceCode;
import com.norcane.zen.source.syntax.CStyleBlockCommentSyntax;
import com.norcane.zen.source.syntax.CommentSyntax;
import com.norcane.zen.support.LanguageSupport;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JavaSupport implements LanguageSupport {

    private static final String NAME = "java";
    private static final Set<String> FILE_EXTENSIONS = Set.of("java");

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Set<String> resourceTypes() {
        return FILE_EXTENSIONS;
    }

    @Override
    public SourceCode analyze(Resource resource) {
        final CommentSyntax commentSyntax = CStyleBlockCommentSyntax.instance();  // FIXME choose this based on configuration

        return new JavaSourceCode(commentSyntax, resource);
    }
}
