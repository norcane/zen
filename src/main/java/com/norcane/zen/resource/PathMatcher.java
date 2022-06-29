package com.norcane.zen.resource;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PathMatcher {

    private static final String PATH_MATCHER_SYNTAX = "glob";
    private static final List<Character> SPECIAL_CHARS = List.of('*', '?', '[');

    public boolean matches(String pattern, Path path) {
        final String syntaxAndPattern = PATH_MATCHER_SYNTAX + ":" + pattern;
        return FileSystems.getDefault().getPathMatcher(syntaxAndPattern).matches(path);
    }

    public boolean matches(String pattern, String path) {
        return matches(pattern, Path.of(path));
    }

    public boolean isPattern(String pattern) {
        return pattern.chars().mapToObj(i -> (char) i).anyMatch(SPECIAL_CHARS::contains);
    }
}
