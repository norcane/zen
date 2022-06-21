package com.norcane.zen.template.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.norcane.zen.template.Template;
import com.norcane.zen.template.TemplateFactory;

import java.io.Reader;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MustacheTemplateFactory implements TemplateFactory {

    private static final List<String> FILE_EXTENSIONS = List.of("mustache");

    private static final MustacheFactory mustacheFactory = new DefaultMustacheFactory();

    @Override
    public List<String> fileExtensions() {
        return FILE_EXTENSIONS;
    }

    @Override
    public Template compile(String name, Reader reader) {
        final Mustache compiled = mustacheFactory.compile(reader, name);

        return new MustacheTemplate(name, compiled);
    }
}