package com.norcane.zen.template.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import com.norcane.zen.resource.Resource;
import com.norcane.zen.template.Template;
import com.norcane.zen.template.TemplateFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MustacheTemplateFactory implements TemplateFactory {

    private static final String TEMPLATE_TYPE = "mustache";

    private static final MustacheFactory mustacheFactory = new DefaultMustacheFactory();

    @Override
    public String templateType() {
        return TEMPLATE_TYPE;
    }

    @Override
    public Template compile(Resource resource) {
        try (final Reader reader = new InputStreamReader(resource.inputStream())) {
            return new MustacheTemplate(resource.location(), mustacheFactory.compile(reader, resource.location()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
