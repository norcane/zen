package com.norcane.zen.support.impl;

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
    public Set<String> fileExtensions() {
        return FILE_EXTENSIONS;
    }
}
