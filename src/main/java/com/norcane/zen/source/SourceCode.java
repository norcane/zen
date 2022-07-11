package com.norcane.zen.source;

import com.norcane.zen.resource.Resource;
import com.norcane.zen.source.header.HeaderSyntax;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SourceCode {

    Optional<Header> header();

    Map<String, Object> variables();

    Resource resource();

    record Header(int startLine, int endLine, List<String> lines) {
    }

    static SourceCode of(HeaderSyntax headerSyntax, Resource resource) {
        return new AbstractSourceCode(headerSyntax, resource) {

            @Override
            public Map<String, Object> variables() {
                return Map.of();
            }
        };
    }
}
