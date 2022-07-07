package com.norcane.zen.template.mustache;

import com.google.common.base.MoreObjects;

import com.github.mustachejava.Mustache;
import com.norcane.zen.template.Template;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Map;

public class MustacheTemplate implements Template {

    final String name;
    final Mustache compiled;

    public MustacheTemplate(String name, Mustache compiled) {
        this.name = name;
        this.compiled = compiled;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Writer render(Writer writer, Map<String, Object> variables) {
        compiled.execute(writer, variables);

        try {
            writer.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return writer;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("name", name)
            .toString();
    }
}
